package net.foxdenstudio.foxcore.impl.object.index.types;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.inject.Injector;
import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.index.WritableIndexBase;
import net.foxdenstudio.foxcore.api.object.index.types.FileIndex;
import net.foxdenstudio.foxcore.api.object.reference.types.IndexReference;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.path.section.ObjectPathSection;
import net.foxdenstudio.foxcore.api.storage.FoxObjectData;
import net.foxdenstudio.foxcore.api.storage.FoxStorageDataClass;
import net.foxdenstudio.foxcore.api.storage.FoxStorageManager;
import net.foxdenstudio.foxcore.api.storage.ISimpleState;
import net.foxdenstudio.foxcore.api.util.NameChecker;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FileIndexImpl extends WritableIndexBase implements FileIndex {

    private final NameChecker nameChecker;
    private final Injector injector;
    private final FoxStorageManager storageManager;
    private final AdapterFactory factory;

    private Path dirPath;
    private Path indexPath;
    private Gson gson;

    @FoxLogger("index.file.impl")
    Logger logger;


    @Inject
    private FileIndexImpl(FoxPathFactory pathFactory, NameChecker nameChecker, Injector injector, FoxStorageManager storageManager, AdapterFactory factory) {
        super(pathFactory);
        this.nameChecker = nameChecker;
        this.injector = injector;
        this.storageManager = storageManager;
        this.factory = factory;

        dirPath = Paths.get("fox");
        indexPath = dirPath.resolve("index.foxcf");

        GsonBuilder gsonBuilder = this.storageManager.getBaseGsonConfig();
        gsonBuilder.registerTypeAdapterFactory(factory);
        gson = gsonBuilder.create();
    }

    @Override
    protected void registerNamespaces() {
        registerNamespace(new FileIndexNamespace(), null);
    }

    @Override
    public void save() {
        Index index = new Index();
        index.objects = new ArrayList<>();

        for (Map.Entry<StandardPathComponent, IndexReference> entry : this.defaultIndexMap.entrySet()) {
            Entry indexEntry = new Entry();
            indexEntry.bundle = new Bundle();
            indexEntry.path = entry.getKey();
            IndexReference reference = entry.getValue();
            if (reference.isValid()) {
                Optional<FoxObject> foxObjectOptional = reference.getObject();
                if (foxObjectOptional.isPresent()) {
                    FoxObject foxObject = foxObjectOptional.get();
                    if (foxObject instanceof ISimpleState) {
                        Class<? extends FoxObject> clazz = foxObject.getClass();
                        indexEntry.bundle.className = clazz.getName();
                        for (Type type : clazz.getGenericInterfaces()) {
                            if (type instanceof ParameterizedType) {
                                ParameterizedType parameterizedType = ((ParameterizedType) type);
                                if (parameterizedType.getRawType().equals(ISimpleState.class)) {
                                    Type dataType = parameterizedType.getActualTypeArguments()[0];
                                    if (dataType instanceof ParameterizedType) {
                                        dataType = ((ParameterizedType) dataType).getRawType();
                                    }
                                    if (dataType instanceof Class) {
                                        Class<?> dataClass = ((Class<?>) dataType);
                                        indexEntry.bundle.dataClassName = dataClass.getName();
                                        FoxStorageDataClass annotation = dataClass.getAnnotation(FoxStorageDataClass.class);
                                        if (annotation != null) {
                                            indexEntry.bundle.dataVersion = annotation.version();
                                            try {
                                                indexEntry.bundle.data = ((ISimpleState<?>) foxObject).getData();
                                                logger.debug("Converted object at \"{}\" to data entry.", indexEntry.path);
                                                index.objects.add(indexEntry);
                                            } catch (Exception e) {
                                                logger.warn("Error getting data for object at \"{}\"! Skipping!", indexEntry.path, e);
                                            }
                                        } else {
                                            logger.warn("For object at \"{}\": its data class type of \"{}\" is missing the @FoxStorageDataClass annotation. Skipping.",
                                                    indexEntry.path, indexEntry.bundle.dataClassName);
                                        }
                                    } else {
                                        logger.error("Yeah I don't know how this happened. Check code and debug.");
                                    }
                                }
                            } else if (type.equals(ISimpleState.class)) {
                                logger.warn("Object at \"{}\" of type \"{}\" implemented ISimpleState incorrectly as a raw type. Skipping.",
                                        indexEntry.path, indexEntry.bundle.className);
                            }
                        }
                    } else {
                        logger.info("Object at \"{}\" does not implement ISimpleState. Skipping.", indexEntry.path);
                    }
                } else {
                    logger.warn("Could not get FoxObject from reference at \"{}\"!", indexEntry.path);
                }
            } else {
                logger.warn("Skipping expired (invalid) reference at \"{}\"!", indexEntry.path);
            }
        }
        logger.info("Converted {} FoxObjects to data entries. Saving...", index.objects.size());
        try {
            Files.createDirectories(dirPath);
            if (Files.notExists(indexPath))
                Files.createFile(indexPath);
            try (JsonWriter writer = new JsonWriter(Files.newBufferedWriter(indexPath))) {
                writer.setIndent("  ");
                this.gson.toJson(index, Index.class, writer);
            }
        } catch (IOException e) {
            logger.error("Error writing to file.", e);
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public void load() {
        if (!this.defaultIndexMap.isEmpty()) {
            logger.warn("Cannot load objects if the index map isn't empty!");
            return;
        }
        if (!Files.isRegularFile(indexPath)) {
            logger.info("No existing index found.");
            return;
        }
        logger.info("Found index file. Opening...");
        try (JsonReader reader = new JsonReader(Files.newBufferedReader(indexPath));) {
            logger.info("Reading index data...");
            Index index = this.gson.fromJson(reader, Index.class);
            logger.info("Constructing and loading fox objects...");
            for (Entry entry : index.objects) {
                try {
                    Class<?> clazz = Class.forName(entry.bundle.className);
                    if (FoxObject.class.isAssignableFrom(clazz) && ISimpleState.class.isAssignableFrom(clazz)) {
                        FoxObject foxObject = this.injector.getInstance((Class<? extends FoxObject>) clazz);
                        boolean success = ((ISimpleState<FoxObjectData>) foxObject).setData(entry.bundle.data);
                        if (success) {
                            this.addObject(foxObject, ObjectPathSection.from(entry.path));
                        }
                    }
                } catch (ClassNotFoundException e) {
                    logger.warn("Could not find class for object at \"{}\". Skipping.", entry.path);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getIndexName() {
        return "file";
    }

    protected class FileIndexNamespace extends NamespaceBase {
    }

    private static class Index {
        short version = 1;
        List<Entry> objects;
    }

    private static class Entry {
        StandardPathComponent path;
        Embed embed;
        Bundle bundle;
    }

    private static class Embed {
        List<StandardPathComponent> path;
        boolean linked;
    }

    private static class Bundle {
        String className;
        String dataClassName;
        int dataVersion;
        FoxObjectData data;
    }

    /**
     * TypeAdapterFactory for creating a type adapter to handle serialization and deserialization of an index entry.
     */
    @Singleton
    private static class AdapterFactory implements TypeAdapterFactory {

        @FoxLogger("index.file.impl.adapter")
        Logger logger;

        @SuppressWarnings("unchecked")
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (type.getRawType().equals(Bundle.class)) {
                return (TypeAdapter<T>) new Adapter(gson);
            } else return null;
        }

        public class Adapter extends TypeAdapter<Bundle> {

            private final TypeAdapter<StandardPathComponent> standardPathComponentTypeAdapter;
            private final TypeAdapter<List<StandardPathComponent>> standardPathComponentListTypeAdapter;
            private final Gson gson;

            public Adapter(Gson gson) {
                this.standardPathComponentTypeAdapter = gson.getAdapter(StandardPathComponent.class);
                this.standardPathComponentListTypeAdapter = gson.getAdapter(new TypeToken<List<StandardPathComponent>>() {
                });
                this.gson = gson;
            }

            @SuppressWarnings("unchecked")
            @Override
            public void write(JsonWriter out, Bundle value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                out.beginObject();
                out.name("class");
                out.value(value.className);
                out.name("dataClass");
                out.value(value.dataClassName);
                out.name("dataVersion");
                out.value(value.dataVersion);
                out.name("data");
                TypeAdapter<FoxObjectData> adapter = ((TypeAdapter<FoxObjectData>) gson.getAdapter(value.data.getClass()));
                adapter.write(out, value.data);
                out.endObject();
            }

            @SuppressWarnings("unchecked")
            @Override
            public Bundle read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                boolean failed = false;
                Bundle bundle = new Bundle();
                in.beginObject();
                while (in.hasNext()) {
                    String name = in.nextName();
                    if (failed) {
                        in.skipValue();
                        continue;
                    }
                    switch (name) {
                        case "class":
                            bundle.className = in.nextString();
                            break;
                        case "dataClass":
                            bundle.dataClassName = in.nextString();
                            break;
                        case "dataVersion":
                            bundle.dataVersion = in.nextInt();
                            break;
                        case "data":
                            if (bundle.dataClassName == null) {
                                failed = true;
                                logger.warn("Data object read before data class name. This adapter does not yet support out-of-order deserialization. Skipping.");
                                in.skipValue();
                            } else {
                                try {
                                    Class<?> clazz = Class.forName(bundle.dataClassName);
                                    if (FoxObjectData.class.isAssignableFrom(clazz)) {
                                        TypeAdapter<? extends FoxObjectData> adapter = gson.getAdapter((Class<? extends FoxObjectData>) clazz);
                                        bundle.data = adapter.read(in);
                                    }
                                } catch (ClassNotFoundException e) {
                                    StringBuilder sb = new StringBuilder("Could not find data class \"")
                                            .append(bundle.dataClassName)
                                            .append("\" for bundle");
                                    if (bundle.className != null && !bundle.className.isEmpty()) {
                                        sb.append(" of type \"")
                                                .append(bundle.className)
                                                .append("\"");
                                    }
                                    logger.warn(sb.toString(), e);
                                }
                            }
                            break;
                        default:
                            in.skipValue();
                            break;
                    }
                }
                in.endObject();
                if (failed) return null;

                return bundle;
            }
        }
    }

}
