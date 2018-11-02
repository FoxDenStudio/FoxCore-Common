package net.foxdenstudio.foxcore.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import net.foxdenstudio.foxcore.api.exception.FoxException;
import net.foxdenstudio.foxcore.api.exception.FoxExceptionFactory;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;

public class ExceptionModule extends AbstractModule {

    public static final ExceptionModule INSTANCE = new ExceptionModule();

    private ExceptionModule() {
    }

    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(FoxException.class, FoxException.class)
                .implement(FoxCommandException.class, FoxCommandException.class)
                .build(FoxExceptionFactory.class)
        );
    }
}
