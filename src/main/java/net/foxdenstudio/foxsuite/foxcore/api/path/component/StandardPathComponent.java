package net.foxdenstudio.foxsuite.foxcore.api.path.component;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class StandardPathComponent implements FoxPathComponent, Iterable<String>, Comparable<StandardPathComponent> {

    /*TODO
     * So I really messed up. Even for an immutable type, it makes sense to have an empty representation
     * Such an example is string, which does have the empty string object.
     * I should not allow nulls to mean something in between API calls,
     * */

    private static final StandardPathComponent EMPTY = new StandardPathComponent(new String[0]);

    private final String[] elements;

    private transient List<String> elementsList;
    private transient int hash;
    private transient boolean zeroHash;

    public static StandardPathComponent of(@Nullable String... parts) {
        if (parts == null || parts.length == 0) return EMPTY;
        for (int i = 0; i < parts.length; i++) {
            String str = parts[i];
            Preconditions.checkNotNull(str, "Element cannot be null! Index: %s", i);
            Preconditions.checkArgument(!str.isEmpty(), "Element cannot be empty! Index: %s", i);
        }
        return new StandardPathComponent(parts);
    }

    public static StandardPathComponent of(String first, String[] next) {
        String[] elements = new String[next.length + 1];
        elements[0] = first;
        System.arraycopy(next, 0, elements, 1, next.length);
        return of(elements);
    }

    public static StandardPathComponent from(@Nullable List<String> elements) {
        if (elements == null || elements.isEmpty()) return EMPTY;
        for (int i = 0; i < elements.size(); i++) {
            String element = elements.get(i);
            Preconditions.checkNotNull(element, "Element cannot be null! Index: %s", i);
            Preconditions.checkArgument(!element.isEmpty(), "Element cannot be empty! Index: %s", i);
        }
        return new StandardPathComponent(elements.toArray(new String[0]));
    }

    public static StandardPathComponent empty() {
        return EMPTY;
    }

    private StandardPathComponent(String[] elements) {
        this.elements = elements;
    }

    public int length() {
        return this.elements.length;
    }

    public boolean isEmpty() {
        return this.elements.length == 0;
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
    public StandardPathComponent concat(StandardPathComponent component) {
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
        if (from >= to) return EMPTY;
        return new StandardPathComponent(Arrays.copyOfRange(this.elements, from, to));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardPathComponent that = (StandardPathComponent) o;
        // This is a special case where comparing two different, hashed paths can shortcut iterative comparison.
        if ((this.hash != 0 || this.zeroHash)
                && (that.hash != 0 || that.zeroHash)
                && this.hash != that.hash) {
            return false;
        }
        return Arrays.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        int h = hash;
        if (h == 0 && !zeroHash) {
            h = Arrays.hashCode(elements);
            if (h == 0) {
                zeroHash = true;
            } else {
                hash = h;
            }
        }
        return h;
    }

    @Override
    public String toString() {
        if(this.elements.length == 0) return "{EMPTY}";
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

    @Override
    public int compareTo(StandardPathComponent o) {
        if (this.elements.length > o.elements.length) return 1;
        if (this.elements.length < o.elements.length) return -1;
        for (int i = 0; i < this.elements.length; i++) {
            int res = this.elements[i].compareTo(o.elements[i]);
            if (res != 0) return res;
        }
        return 0;
    }

    public static class Adapter extends TypeAdapter<StandardPathComponent> {

        public static final Adapter INSTNACE = new Adapter();

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
                return EMPTY;
            return from(elements);
        }
    }

}
