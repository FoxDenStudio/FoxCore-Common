package net.foxdenstudio.foxsuite.foxcore.api.storage;

import net.foxdenstudio.foxsuite.foxcore.api.object.reference.FoxObjectReference;

public interface StoredReference extends FoxObjectReference {

    StorageAdapter getAdapter();
}
