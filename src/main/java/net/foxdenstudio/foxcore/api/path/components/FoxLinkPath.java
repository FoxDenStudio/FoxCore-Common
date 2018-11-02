package net.foxdenstudio.foxcore.api.path.components;

import net.foxdenstudio.foxcore.api.path.FoxHierarchicalPath;
import net.foxdenstudio.foxcore.api.path.FoxOptionalPath;
import net.foxdenstudio.foxcore.api.path.FoxPath;

public interface FoxLinkPath extends FoxHierarchicalPath, FoxOptionalPath {

    boolean equals(Object o);

    int hashCode();

    String toString();

}
