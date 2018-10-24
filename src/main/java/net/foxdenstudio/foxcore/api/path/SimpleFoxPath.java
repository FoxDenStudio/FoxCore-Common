package net.foxdenstudio.foxcore.api.path;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * A generic immutable sequence of {@link String} elements.
 * Used in various places for pathing... anything really.
 * <p>
 * It's value equal, making it suitable for keys.
 */
public class SimpleFoxPath {

    private static final SimpleFoxPath ROOT = new SimpleFoxPath(new String[0]);

    @Nonnull
    private String[] elements;

    private transient int hash;
    private transient boolean hashed = false;

    private SimpleFoxPath(@Nonnull String[] elements) {
        this.elements = elements;
    }

    public static SimpleFoxPath root() {
        return ROOT;
    }

    public static SimpleFoxPath fromString(String input){
        return null;
    }

    public boolean isRoot() {
        return elements.length == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleFoxPath foxPath = (SimpleFoxPath) o;
        return Arrays.equals(elements, foxPath.elements);
    }

    @Override
    public int hashCode() {
        if (!hashed) {
            hash = Arrays.hashCode(elements);
            hashed = true;
        }
        return hash;
    }

    @Override
    public String toString() {
        if (elements.length == 0) return "./";
        else {
            return String.join("/", this.elements);
        }
    }

}
