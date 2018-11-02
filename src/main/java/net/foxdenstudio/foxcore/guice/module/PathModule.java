package net.foxdenstudio.foxcore.guice.module;

import com.google.inject.AbstractModule;
import net.foxdenstudio.foxcore.api.path.factory.*;
import net.foxdenstudio.foxcore.impl.path.factory.*;

public class PathModule extends AbstractModule {

    public static final PathModule INSTANCE = new PathModule();

    private PathModule() {
    }

    protected void configure() {
        install(UtilModule.INSTANCE);
        bind(FoxObjectPathFactory.class).to(FoxObjectPathFactoryImpl.class);
        bind(FoxLinkPathFactory.class).to(FoxLinkPathFactoryImpl.class);
        bind(FoxNamespacePathFactory.class).to(FoxNamespacePathFactoryImpl.class);
        bind(FoxIndexPathFactory.class).to(FoxIndexPathFactoryImpl.class);
        bind(FoxFullPathFactory.class).to(FoxFullPathFactoryImpl.class);
    }
}
