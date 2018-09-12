package net.foxdenstudio.foxcore.common.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import net.foxdenstudio.foxcore.common.guice.injector.logger.FoxLoggerTypeListener;

public class LoggerInjectorModule extends AbstractModule {
    @Override
    protected void configure() {
        bindListener(Matchers.any(), new FoxLoggerTypeListener());
    }
}
