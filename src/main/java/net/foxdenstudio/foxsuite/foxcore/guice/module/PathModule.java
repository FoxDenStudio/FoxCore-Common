package net.foxdenstudio.foxsuite.foxcore.guice.module;

import com.google.inject.AbstractModule;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPathExt;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxsuite.foxcore.impl.path.FoxPathExtImpl;
import net.foxdenstudio.foxsuite.foxcore.impl.path.FoxPathFactoryImpl;

public class PathModule extends AbstractModule {

    public static final PathModule INSTANCE = new PathModule();

    private PathModule() {
    }

    protected void configure() {
        install(UtilModule.INSTANCE);
        bind(FoxPathExt.Builder.class).to(FoxPathExtImpl.Builder.class);
        bind(FoxPathFactory.class).to(FoxPathFactoryImpl.class);
    }
}
