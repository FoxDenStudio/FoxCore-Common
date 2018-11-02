package net.foxdenstudio.foxcore.api.path.components;

import net.foxdenstudio.foxcore.api.path.FoxOptionalPath;

public interface FoxIndexPath extends FoxOptionalPath {

    String getIndexType();

    FoxNamespacePath getNamespacePath();

    boolean equals(Object o);

    int hashCode();

    String toString();
}
