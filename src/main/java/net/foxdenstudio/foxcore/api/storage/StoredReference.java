package net.foxdenstudio.foxcore.api.storage;

import net.foxdenstudio.foxcore.api.object.reference.FoxObjectReference;

public interface StoredReference extends FoxObjectReference {

    StorageAdapter getAdapter();
}
