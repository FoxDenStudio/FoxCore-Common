package net.foxdenstudio.foxcore.guice.module;

import com.google.inject.AbstractModule;
import net.foxdenstudio.foxcore.api.path.FoxPathExt;
import net.foxdenstudio.foxcore.impl.path.FoxPathExtImpl;

public class PathModule extends AbstractModule {

    public static final PathModule INSTANCE = new PathModule();

    private PathModule() {
    }

    protected void configure() {
        install(UtilModule.INSTANCE);
        bind(FoxPathExt.Builder.class).to(FoxPathExtImpl.Builder.class);
    }
}
