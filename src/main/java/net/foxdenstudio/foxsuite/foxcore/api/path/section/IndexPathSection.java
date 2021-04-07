package net.foxdenstudio.foxsuite.foxcore.api.path.section;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Objects;

public final class IndexPathSection implements FoxPathSection {

    private final String index;
    private final StandardPathComponent namespace;

    public IndexPathSection(@Nonnull String index, @Nullable StandardPathComponent namespace) {
        Preconditions.checkNotNull(index);
        Preconditions.checkArgument(!index.isEmpty(), "Index name cannot be empty!");
        this.index = index;
        this.namespace = namespace == null ? StandardPathComponent.empty() : namespace;
    }

    public String getIndex() {
        return this.index;
    }

    public StandardPathComponent getNamespacePath() {
        return this.namespace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexPathSection that = (IndexPathSection) o;
        return index.equals(that.index) &&
                namespace.equals(that.namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, namespace);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(index);
        if (this.namespace != null && !this.namespace.isEmpty()) {
            builder.append('/').append(namespace);
        }
        return builder.toString();
    }

    public static class Adapter extends TypeAdapter<IndexPathSection> {

        private final Gson gson;
        private final TypeAdapter<StandardPathComponent> spcAdapter;

        public Adapter(Gson gson) {
            this.gson = gson;
            this.spcAdapter = gson.getAdapter(StandardPathComponent.class);
        }

        @Override
        public void write(JsonWriter out, IndexPathSection value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.beginObject();
            out.name("index");
            out.value(value.index);
            out.name("namespace");
            this.spcAdapter.write(out, value.namespace);
            out.endObject();
        }

        @Override
        public IndexPathSection read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            String index = null;
            StandardPathComponent namespace = null;
            in.beginObject();
            while(in.hasNext()){
                String name = in.nextName();
                switch (name){
                    case "index":
                        index = in.nextString();
                        break;
                    case "namespace":
                        namespace = this.spcAdapter.read(in);
                        break;
                    default:
                        in.skipValue();
                        break;
                }
            }
            in.endObject();
            if(index == null || index.isEmpty()) return null;

            return new IndexPathSection(index, namespace) ;
        }
    }
}
