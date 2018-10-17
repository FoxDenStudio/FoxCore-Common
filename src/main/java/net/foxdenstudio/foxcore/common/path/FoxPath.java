package net.foxdenstudio.foxcore.common.path;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * A generic immutable sequence of {@link String} elements.
 * Used in various places for pathing... anything really.
 * <p>
 * It's value equal, making it suitable for keys.
 */
public class FoxPath {

    private static final FoxPath ROOT = new FoxPath(new String[0]);

    @Nonnull
    private String[] elements;

    private transient int hash;
    private transient boolean hashed = false;

    private FoxPath(@Nonnull String[] elements) {
        this.elements = elements;
    }

    public static FoxPath root() {
        return ROOT;
    }

    public static FoxPath fromString(String input){

    }

    public boolean isRoot() {
        return elements.length == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoxPath foxPath = (FoxPath) o;
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
