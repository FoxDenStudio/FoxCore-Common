package net.foxdenstudio.foxsuite.foxcore.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import net.foxdenstudio.foxsuite.foxcore.api.annotation.command.FoxMainDispatcher;
import net.foxdenstudio.foxsuite.foxcore.api.command.context.FoxCommandContextManager;
import net.foxdenstudio.foxsuite.foxcore.api.command.context.CommandContext;
import net.foxdenstudio.foxsuite.foxcore.api.command.result.ResultFactory;
import net.foxdenstudio.foxsuite.foxcore.api.command.standard.FoxCommandDispatcher;
import net.foxdenstudio.foxsuite.foxcore.impl.command.CommandDispatcherImpl;
import net.foxdenstudio.foxsuite.foxcore.impl.command.context.FoxCommandContextManagerImpl;
import net.foxdenstudio.foxsuite.foxcore.impl.command.context.CommandContextImpl;
import net.foxdenstudio.foxsuite.foxcore.impl.command.result.ResultFactoryImpl;

public class CommandModule extends AbstractModule {

    public static final CommandModule INSTANCE = new CommandModule();

    private CommandModule() {
    }

    protected void configure() {
        bind(FoxCommandDispatcher.class)
                .annotatedWith(FoxMainDispatcher.class)
                .to(CommandDispatcherImpl.class)
                .in(Singleton.class);
        bind(ResultFactory.class).to(ResultFactoryImpl.class);
        bind(FoxCommandContextManager.class).to(FoxCommandContextManagerImpl.class);
        bind(CommandContext.class).to(CommandContextImpl.class);
    }
}
