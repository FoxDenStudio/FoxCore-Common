package net.foxdenstudio.foxsuite.foxcore.impl.world;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.foxdenstudio.foxsuite.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxsuite.foxcore.api.path.section.ObjectPathSection;
import net.foxdenstudio.foxsuite.foxcore.api.storage.FoxStorageManager;
import net.foxdenstudio.foxsuite.foxcore.api.world.FoxWorld;
import net.foxdenstudio.foxsuite.foxcore.api.world.FoxWorldManager;
import net.foxdenstudio.foxsuite.foxcore.platform.world.World;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public abstract class FoxWorldManagerImplBase implements FoxWorldManager {

    protected final FoxStorageManager storageManager;
    protected final FoxMainIndex mainIndex;
    protected final FoxWorldImpl.RepObjectFactory foxWorldImplObjectFactory;

    protected Map<String, FoxWorldImpl> worldMap = new HashMap<>();
    protected transient Map<String, FoxWorldImpl> worldMapCopy = ImmutableMap.copyOf(worldMap);
    protected transient Map<World, FoxWorldImpl> weakOnlineMap = new WeakHashMap<>();


    protected boolean loaded;
    protected Path dirPath;
    protected Path worldIndexPath;
    protected Gson gson;

    @FoxLogger("manager.world.impl")
    protected Logger logger;

    @Inject
    protected FoxWorldManagerImplBase(FoxStorageManager storageManager, FoxMainIndex mainIndex, FoxWorldImpl.RepObjectFactory foxWorldImplObjectFactory) {
        this.storageManager = storageManager;
        this.mainIndex = mainIndex;
        this.foxWorldImplObjectFactory = foxWorldImplObjectFactory;
        dirPath = Paths.get("fox");
        worldIndexPath = dirPath.resolve("worlds.foxcf");

        GsonBuilder gsonBuilder = storageManager.getBaseGsonConfig();
        gson = gsonBuilder.create();
    }

    @Override
    public Optional<World> getOnlineWorld(String name) {
        return this.getWorld(name).flatMap(FoxWorld::getOnlineWorld);
    }

    @Override
    public Optional<FoxWorld> getWorld(String name) {
        return Optional.ofNullable(worldMapCopy.get(name));
    }

    @Override
    public FoxWorld getWorld(@Nonnull World world) {
        return this.weakOnlineMap.get(world);
    }

    @Override
    public Map<String, World> getOnlineWorlds() {
        return ImmutableMap.of();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, FoxWorld> getWorlds() {
        return (Map<String, FoxWorld>) (Map<String, ? extends FoxWorld>) worldMapCopy;
    }

    @Override
    public void load() {
        if (this.loaded) {
            logger.warn("Already loaded data once!");
            return;
        }
        if (!this.worldMap.isEmpty()) {
            logger.error("Can't load world data into existing non-empty map.");
            return;
        }
        if (!Files.isRegularFile(worldIndexPath)) {
            logger.info("No existing index found.");
            return;
        }
        logger.info("Found world index file. Opening...");
        try (JsonReader reader = new JsonReader(Files.newBufferedReader(worldIndexPath));) {
            logger.info("Reading world index data...");
            Index index = this.gson.fromJson(reader, Index.class);
            logger.info("Constructing and loading fox world entries...");

            for (Entry entry : index.worlds) {
                FoxWorldImpl foxWorld = new FoxWorldImpl(entry.name, entry.uuid, entry.path);
                this.worldMap.put(entry.name, foxWorld);
                FoxWorldImpl.FoxObject foxWorldObj = configureAndLoadWorldRep(foxWorld);
                // TODO load links.
            }
            this.worldMapCopy = ImmutableMap.copyOf(this.worldMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected FoxWorldImpl.FoxObject configureAndLoadWorldRep(FoxWorldImpl world) {
        FoxWorldImpl.FoxObject obj = this.foxWorldImplObjectFactory.get(world);
        world.setRepresentation(obj);
        // TODO configure link format.
        this.mainIndex.getMemoryIndex().addObject(obj, ObjectPathSection.of("world", world.getName()));
        return obj;
    }

    @Override
    public void save() {
        logger.info("Saving fox-world index...");
        Index index = new Index();
        index.worlds = new ArrayList<>();

        for (Map.Entry<String, FoxWorldImpl> entry : this.worldMap.entrySet()) {
            Entry worldEntry = new Entry();
            FoxWorldImpl world = entry.getValue();
            worldEntry.name = entry.getKey();
            worldEntry.uuid = world.getUniqueId();
            worldEntry.path = world.getDirectory().orElse(null);

            //TODO write links.

            index.worlds.add(worldEntry);
        }

        logger.info("Converted {} FoxWorld objects to data entries. Saving...", index.worlds.size());

        try {
            Files.createDirectories(dirPath);
            if (Files.notExists(worldIndexPath))
                Files.createFile(worldIndexPath);
            try (JsonWriter writer = new JsonWriter(Files.newBufferedWriter(worldIndexPath))) {
                writer.setIndent("  ");
                this.gson.toJson(index, Index.class, writer);
            }
        } catch (IOException e) {
            logger.error("Error writing to file.", e);
        }
    }

    protected static class Index {
        short version = 1;
        List<Entry> worlds;
    }

    protected static class Entry {
        String name;
        UUID uuid;
        transient Path path;
        Map<StandardPathComponent, FoxPath> links;
    }
}
