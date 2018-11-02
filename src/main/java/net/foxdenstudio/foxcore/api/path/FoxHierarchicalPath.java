package net.foxdenstudio.foxcore.api.path;

public interface FoxHierarchicalPath extends FoxPath {

    int numElements();

    String getElement(int index);
}
