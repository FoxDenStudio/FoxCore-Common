package net.foxdenstudio.foxcore.api.path.section;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public final class ObjectPathSection implements FoxPathSection {

    private final StandardPathComponent path;

    public ObjectPathSection(@Nonnull StandardPathComponent path) {
        Preconditions.checkNotNull(path);
        this.path = path;
    }

    public StandardPathComponent getPathComponent() {
        return this.path;
    }

    public static ObjectPathSection of(@Nonnull String first, String... next) {
        return new ObjectPathSection(StandardPathComponent.of(first, next));
    }

    public static ObjectPathSection from(@Nonnull List<String> elements) {
        return new ObjectPathSection(StandardPathComponent.from(elements));
    }

    public static ObjectPathSection from(@Nonnull StandardPathComponent path) {
        return new ObjectPathSection(path);
    }

    public int numParts() {
        return path.length();
    }

    public List<String> getElements() {
        return path.elements();
    }

    @Nonnull
    public String getPart(int index) {
        return path.get(index);
    }

    @Nonnull
    public StandardPathComponent resolve(StandardPathComponent component) {
        return path.concat(component);
    }

    @Override
    public String toString() {
        return path.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectPathSection that = (ObjectPathSection) o;
        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    public static class Adapter extends TypeAdapter<ObjectPathSection> {

        private final Gson gson;
        private final TypeAdapter<StandardPathComponent> spcAdapter;

        public Adapter(Gson gson) {
            this.gson = gson;
            this.spcAdapter = gson.getAdapter(StandardPathComponent.class);
        }

        @Override
        public void write(JsonWriter out, ObjectPathSection value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            this.spcAdapter.write(out, value.path);
        }

        @Override
        public ObjectPathSection read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            StandardPathComponent path = this.spcAdapter.read(in);
            if (path == null || path.isEmpty()) return null;

            return ObjectPathSection.from(path);
        }
    }
}
