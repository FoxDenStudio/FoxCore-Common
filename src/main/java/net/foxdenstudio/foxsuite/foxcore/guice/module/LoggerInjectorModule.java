package net.foxdenstudio.foxsuite.foxcore.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import net.foxdenstudio.foxsuite.foxcore.guice.injector.logger.FoxLoggerTypeListener;

public class LoggerInjectorModule extends AbstractModule {

    public static final LoggerInjectorModule INSTANCE = new LoggerInjectorModule();

    private LoggerInjectorModule() {
    }

    @Override
    protected void configure() {
        bindListener(Matchers.any(), new FoxLoggerTypeListener());
    }
}
