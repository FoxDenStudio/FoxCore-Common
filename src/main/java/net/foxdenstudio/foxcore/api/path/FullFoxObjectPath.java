package net.foxdenstudio.foxcore.api.path;

import java.util.Collection;

/**
 * Fully qualified path of any fox-object.
 * Contains index, object, and link paths.
 * Must be value equal and hashable.
 */
public interface FullFoxObjectPath {

    FoxIndexPath getIndexPath();

    FoxObjectPath getObjectPath();

    Collection<FoxLinkPath> getLinkPaths();

    boolean equals(Object o);

    int hashCode();

    String toString();

}
