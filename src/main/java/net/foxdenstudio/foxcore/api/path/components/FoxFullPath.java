package net.foxdenstudio.foxcore.api.path.components;

import net.foxdenstudio.foxcore.api.path.FoxPath;

import java.util.Collection;
import java.util.List;

/**
 * Fully qualified path of any fox-object.
 * Contains index, object, and link paths.
 * Must be value equal and hashable.
 */
public interface FoxFullPath extends FoxPath {

    FoxIndexPath getIndexPath();

    FoxObjectPath getObjectPath();

    List<FoxLinkPath> getLinkPaths();

    boolean equals(Object o);

    int hashCode();

    String toString();

}
