package net.foxdenstudio.foxsuite.foxcore.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.types.FileIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.types.MemoryIndex;
import net.foxdenstudio.foxsuite.foxcore.impl.object.index.FoxMainIndexImpl;
import net.foxdenstudio.foxsuite.foxcore.impl.object.index.types.FileIndexImpl;
import net.foxdenstudio.foxsuite.foxcore.impl.object.index.types.MemoryIndexImpl;

public class IndexModule extends AbstractModule {

    public static final IndexModule INSTANCE = new IndexModule();

    private IndexModule() {
    }

    protected void configure() {
        //add configuration logic here
        bind(MemoryIndex.class)
                .to(MemoryIndexImpl.class);
        bind(FileIndex.class)
                .to(FileIndexImpl.class);
        bind(FoxMainIndex.class)
                .to(FoxMainIndexImpl.class)
                .in(Singleton.class);
    }
}
