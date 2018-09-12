package net.foxdenstudio.foxcore.common.guice.module;

import com.google.inject.AbstractModule;


public class FoxCoreModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new LoggerInjectorModule());
    }
}
