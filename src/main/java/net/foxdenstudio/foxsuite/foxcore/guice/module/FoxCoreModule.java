package net.foxdenstudio.foxsuite.foxcore.guice.module;

import com.google.inject.AbstractModule;
import net.foxdenstudio.foxsuite.foxcore.api.FoxRegistry;
import net.foxdenstudio.foxsuite.foxcore.api.region.cache.NullRegionCache;
import net.foxdenstudio.foxsuite.foxcore.api.region.cache.RegionCache;
import net.foxdenstudio.foxsuite.foxcore.impl.FoxRegistryImpl;


public class FoxCoreModule extends AbstractModule {
    @Override
    protected void configure() {
        install(LoggerInjectorModule.INSTANCE);
        install(CommandModule.INSTANCE);
        install(PathModule.INSTANCE);
        install(IndexModule.INSTANCE);
        bind(FoxRegistry.class).to(FoxRegistryImpl.class);

        bind(RegionCache.class).to(NullRegionCache.class);
    }
}
