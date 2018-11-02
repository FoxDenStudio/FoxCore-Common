package net.foxdenstudio.foxcore.api.path.components;

import net.foxdenstudio.foxcore.api.path.FoxHierarchicalPath;
import net.foxdenstudio.foxcore.api.path.FoxPath;

/**
 * Path of fox-object inside an index.
 * Must be value equal and hashable, as it is used as a key.
 */
public interface FoxObjectPath extends FoxHierarchicalPath {

    boolean equals(Object o);

    int hashCode();

    String toString();

}
