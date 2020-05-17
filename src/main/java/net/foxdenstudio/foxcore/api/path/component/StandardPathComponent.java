package net.foxdenstudio.foxcore.api.path.component;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class StandardPathComponent implements FoxPathComponent, Iterable<String> {

    /*TODO
    * So I really messed up. Even for an immutable type, it makes sense to have an empty representation
    * Such an example is string, which does have the empty string object.
    * I should not allow nulls to mean something in between API calls,
    * */

    private final String[] elements;
    private transient List<String> elementsList;

    public static StandardPathComponent of(@Nonnull String first, String... next) {
        String[] elements;
        Preconditions.checkNotNull(first, "Element cannot be null! Index: 0");
        Preconditions.checkArgument(!first.isEmpty(), "Element cannot be empty! Index: 0");
        if (next == null) {
            elements = new String[]{first};
        } else {
            elements = new String[next.length + 1];
            elements[0] = first;
            for (int i = 0; i < next.length; i++) {
                String str = next[i];
                Preconditions.checkNotNull(str, "Element cannot be null! Index: %s", i + 1);
                Preconditions.checkArgument(!str.isEmpty(), "Element cannot be empty! Index: %s", i + 1);
                elements[i + 1] = str;
            }
        }
        return new StandardPathComponent(elements);
    }

    public static StandardPathComponent from(@Nonnull List<String> elements) {
        Preconditions.checkNotNull(elements);
        Preconditions.checkArgument(elements.size() > 0, "Must have at least one element!");
        for (int i = 0; i < elements.size(); i++) {
            String element = elements.get(i);
            Preconditions.checkNotNull(element, "Element cannot be null! Index: %s", i);
            Preconditions.checkArgument(!element.isEmpty(), "Element cannot be empty! Index: %s", i);
        }
        return new StandardPathComponent(elements.toArray(new String[0]));
    }

    private StandardPathComponent(String[] elements) {
        this.elements = elements;
    }

    public int size() {
        return elements.length;
    }

    public List<String> elements() {
        if (elementsList == null) {
            elementsList = ImmutableList.copyOf(elements);
        }
        return elementsList;
    }

    @Nonnull
    public String get(int index) {
        Preconditions.checkElementIndex(index, elements.length);
        return elements[index];
    }

    @Nonnull
    public StandardPathComponent resolve(StandardPathComponent component) {
        String[] newElements = Arrays.copyOf(this.elements, this.elements.length + component.elements.length);
        System.arraycopy(component.elements, 0, newElements, this.elements.length, component.elements.length);
        return new StandardPathComponent(newElements);
    }

    public StandardPathComponent subPath(int start) {
        return subPath(start, this.elements.length);
    }

    public StandardPathComponent subPath(int from, int to) {
        Preconditions.checkArgument(from >= 0 && from <= this.elements.length,
                "start index out of bounds: [ 0 - " + (this.elements.length) + " ] : " + from);
        Preconditions.checkArgument(to >= 0 && to <= this.elements.length,
                "end index out of bounds: [ 0 - " + this.elements.length + " ] : " + to);
        if (from == this.elements.length) return null;
        return new StandardPathComponent(Arrays.copyOfRange(this.elements, from, to));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardPathComponent that = (StandardPathComponent) o;
        return Arrays.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(elements[0]);
        for (int i = 1; i < elements.length; i++) {
            builder.append('/').append(elements[i]);
        }
        return builder.toString();
    }

    @Override
    public Iterator<String> iterator() {
        return this.elements().iterator();
    }

    public static class Adapter extends TypeAdapter<StandardPathComponent> {

        @Override
        public void write(JsonWriter out, StandardPathComponent value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.beginArray();
            for (String element : value.elements) {
                out.value(element);
            }
            out.endArray();
        }

        @Override
        public StandardPathComponent read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            List<String> elements = new ArrayList<>();
            in.beginArray();
            while (in.hasNext()) {
                if (in.peek() == JsonToken.STRING) {
                    elements.add(in.nextString());
                } else {
                    in.skipValue();
                }
            }
            in.endArray();
            if (elements.isEmpty())
                return null;
            return from(elements);
        }
    }

}
