package net.foxdenstudio.foxcore.guice.module;

import com.google.inject.AbstractModule;


public class FoxCoreModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new LoggerInjectorModule());
        install(new CommandModule());
        install(ExceptionModule.INSTANCE);
        install(PathModule.INSTANCE);
    }
}
