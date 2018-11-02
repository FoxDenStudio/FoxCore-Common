package net.foxdenstudio.foxcore.impl.path;

import net.foxdenstudio.foxcore.api.path.FoxHierarchicalPath;
import net.foxdenstudio.foxcore.api.path.FoxOptionalPath;
import net.foxdenstudio.foxcore.api.path.components.FoxLinkPath;
import net.foxdenstudio.foxcore.api.path.components.FoxNamespacePath;
import net.foxdenstudio.foxcore.api.path.components.FoxObjectPath;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * A generic immutable sequence of {@link String} elements.
 * Used in various places for pathing... anything really.
 * <p>
 * It's value equal, making it suitable for keys.
 */
public class FoxHierarchicalPathImpl implements FoxHierarchicalPath, FoxOptionalPath, FoxObjectPath, FoxLinkPath, FoxNamespacePath {

    private static final FoxHierarchicalPathImpl ROOT = new FoxHierarchicalPathImpl(new String[0]);

    @Nonnull
    private final String[] elements;

    private transient int hash;
    private transient boolean hashed = false;

    private FoxHierarchicalPathImpl(@Nonnull String[] elements) {
        this.elements = elements;
    }

    public static FoxHierarchicalPathImpl root() {
        return ROOT;
    }

    public static FoxHierarchicalPathImpl of(String... elements){
        if (elements == null || elements.length == 0) return root();

        // TODO perform input validation maybe?

        return new FoxHierarchicalPathImpl(elements);
    }

    public boolean isRoot() {
        return elements.length == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoxHierarchicalPathImpl foxPath = (FoxHierarchicalPathImpl) o;
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
        if (elements.length == 0) return "";
        else {
            return String.join("/", this.elements);
        }
    }

    @Override
    public int numElements() {
        return this.elements.length;
    }

    @Override
    public String getElement(int index) {
        return this.elements[index];
    }

    @Override
    public boolean isEmpty() {
        return this.elements.length == 0;
    }

}
