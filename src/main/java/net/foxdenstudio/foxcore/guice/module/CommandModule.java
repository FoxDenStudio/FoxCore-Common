package net.foxdenstudio.foxcore.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import net.foxdenstudio.foxcore.api.annotation.command.FoxMainDispatcher;
import net.foxdenstudio.foxcore.api.command.FoxCommandDispatcher;
import net.foxdenstudio.foxcore.api.command.result.ResultFactory;
import net.foxdenstudio.foxcore.impl.command.CommandDispatcherImpl;
import net.foxdenstudio.foxcore.impl.command.result.ResultFactoryImpl;

public class CommandModule extends AbstractModule {
    protected void configure() {
        bind(FoxCommandDispatcher.class)
                .annotatedWith(FoxMainDispatcher.class)
                .to(CommandDispatcherImpl.class)
                .in(Singleton.class);
        bind(ResultFactory.class).to(ResultFactoryImpl.class);

    }
}
