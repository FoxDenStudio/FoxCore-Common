package net.foxdenstudio.foxcore.api.path.section;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.*;

public final class LinkPathSection implements FoxPathSection {

    private final List<StandardPathComponent> links;

    public static LinkPathSection of(@Nonnull StandardPathComponent first, StandardPathComponent... next) {
        Preconditions.checkNotNull(first);
        ImmutableList.Builder<StandardPathComponent> builder = ImmutableList.builder();
        builder.add(first);

        if (next != null) {
            for (StandardPathComponent comp : next) {
                if (comp != null) {
                    builder.add(comp);
                }
            }
        }
        return new LinkPathSection(builder.build());
    }

    public static LinkPathSection of(@Nonnull LinkPathSection linkPathSection, StandardPathComponent... next) {
        return of(linkPathSection.links, next);
    }

    public static LinkPathSection of(@Nonnull List<StandardPathComponent> components, StandardPathComponent... next) {
        ImmutableList.Builder<StandardPathComponent> builder = ImmutableList.builder();

        components.stream().filter(Objects::nonNull).forEach(builder::add);
        if (next != null) {
            Arrays.stream(next).filter(Objects::nonNull).forEach(builder::add);
        }

        List<StandardPathComponent> list = builder.build();
        Preconditions.checkArgument(!list.isEmpty(), "Must supply at least one non-null element!");
        return new LinkPathSection(list);
    }

    private LinkPathSection(List<StandardPathComponent> links) {
        this.links = links;
    }

    public List<StandardPathComponent> getLinkComponents() {
        return links;
    }

    @Override
    public String toString() {
        Iterator<StandardPathComponent> it = links.iterator();
        StringBuilder builder = new StringBuilder();
        while (it.hasNext()) {
            StandardPathComponent comp = it.next();
            builder.append(comp);
            if (it.hasNext()) {
                builder.append(':');
            }
        }
        return builder.toString();
    }

    public static class Adapter extends TypeAdapter<LinkPathSection> {

        private final Gson gson;
        private final TypeAdapter<StandardPathComponent> spcAdapter;

        public Adapter(Gson gson) {
            this.gson = gson;
            this.spcAdapter = gson.getAdapter(StandardPathComponent.class);
        }

        @Override
        public void write(JsonWriter out, LinkPathSection value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.beginArray();
            for (StandardPathComponent link : value.links) {
                this.spcAdapter.write(out, link);
            }
            out.endArray();
        }

        @Override
        public LinkPathSection read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            List<StandardPathComponent> links = new ArrayList<>();
            in.beginArray();
            while (in.hasNext()) {
                links.add(this.spcAdapter.read(in));
            }
            in.endArray();
            if (links.isEmpty())
                return null;
            return LinkPathSection.of(links);
        }
    }

}
