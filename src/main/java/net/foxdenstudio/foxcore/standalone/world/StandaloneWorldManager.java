package net.foxdenstudio.foxcore.standalone.world;

import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.storage.FoxStorageManager;
import net.foxdenstudio.foxcore.impl.world.FoxWorldImpl;
import net.foxdenstudio.foxcore.impl.world.FoxWorldManagerImplBase;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StandaloneWorldManager extends FoxWorldManagerImplBase {

    @Inject
    private StandaloneWorldManager(FoxStorageManager storageManager, FoxMainIndex mainIndex, FoxWorldImpl.RepObjectFactory foxWorldImplObjectFactory) {
        super(storageManager, mainIndex, foxWorldImplObjectFactory);
    }
}
