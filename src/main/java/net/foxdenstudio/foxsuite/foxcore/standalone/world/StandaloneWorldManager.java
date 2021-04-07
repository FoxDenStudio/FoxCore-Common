package net.foxdenstudio.foxsuite.foxcore.standalone.world;

import net.foxdenstudio.foxsuite.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxsuite.foxcore.api.storage.FoxStorageManager;
import net.foxdenstudio.foxsuite.foxcore.api.world.FoxWorld;
import net.foxdenstudio.foxsuite.foxcore.impl.world.FoxWorldImpl;
import net.foxdenstudio.foxsuite.foxcore.impl.world.FoxWorldManagerImplBase;
import net.foxdenstudio.foxsuite.foxcore.platform.world.World;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StandaloneWorldManager extends FoxWorldManagerImplBase {

    @Inject
    private StandaloneWorldManager(FoxStorageManager storageManager, FoxMainIndex mainIndex, FoxWorldImpl.RepObjectFactory foxWorldImplObjectFactory) {
        super(storageManager, mainIndex, foxWorldImplObjectFactory);
    }

    @Override
    public FoxWorld getWorld(@Nonnull World world) {
        throw new UnsupportedOperationException("There should be no online worlds in standalone mode.");
    }
}
