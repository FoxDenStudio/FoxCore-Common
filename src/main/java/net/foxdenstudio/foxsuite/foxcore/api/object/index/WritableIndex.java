package net.foxdenstudio.foxsuite.foxcore.api.object.index;

import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;

public interface WritableIndex extends WritableNamespace, FoxObjectIndex {

    //TODO Should writable and read-only namespaces be able to co-exist in an index?

    @Override
    WritableNamespace getDefaultNamespace();

    /**
     * Sets the default namespace.
     * @param indexPath
     * @return
     */
    @Override
    boolean setDefaultNamespace(StandardPathComponent indexPath);
}
