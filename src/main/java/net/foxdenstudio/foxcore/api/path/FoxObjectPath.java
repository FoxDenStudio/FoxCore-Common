package net.foxdenstudio.foxcore.api.path;

/**
 * Path of fox-object inside an index.
 * Must be value equal and hashable, as it is used as a key.
 */
public interface FoxObjectPath {

    boolean equals(Object o);

    int hashCode();

    String toString();

}
