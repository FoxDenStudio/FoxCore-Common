package net.foxdenstudio.foxcore.api.object.index.types;

import net.foxdenstudio.foxcore.api.object.index.WritableIndex;

public interface StorageIndex extends WritableIndex {

    void save();

    void load();
}
