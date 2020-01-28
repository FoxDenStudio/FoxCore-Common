package net.foxdenstudio.foxcore.impl.command.context;

import net.foxdenstudio.foxcore.api.command.context.FoxCommandContextManager;
import net.foxdenstudio.foxcore.api.command.context.CommandContext;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.entity.living.player.Player;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Map;
import java.util.WeakHashMap;

@Singleton
public class FoxCommandContextManagerImpl implements FoxCommandContextManager {

    private final Provider<CommandContext> contextProvider;

    private final Map<CommandSource, CommandContext> contexts = new WeakHashMap<>();

    @Inject
    private FoxCommandContextManagerImpl(Provider<CommandContext> contextProvider){
        this.contextProvider = contextProvider;
    }

    @Override
    public CommandContext getCommandContext(CommandSource source) {
        return this.contexts.computeIfAbsent(source, (k-> this.contextProvider.get()));
    }

}
