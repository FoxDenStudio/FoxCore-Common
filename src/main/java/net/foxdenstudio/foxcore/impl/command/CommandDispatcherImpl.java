package net.foxdenstudio.foxcore.impl.command;

import com.google.common.collect.ImmutableSet;
import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.api.command.FoxCommandBase;
import net.foxdenstudio.foxcore.api.command.standard.FoxCommandDispatcher;
import net.foxdenstudio.foxcore.api.command.standard.FoxCommandMapping;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommand;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.command.PlatformCommand;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class CommandDispatcherImpl extends FoxStandardCommandBase implements FoxCommandDispatcher {

    @FoxLogger("command.dispatcher")
    Logger logger;

    private final Map<String, CommandMappingImpl> commandMap = new HashMap<>();

    @Inject
    protected CommandDispatcherImpl() {
    }

    @Override
    public Optional<FoxCommandMapping> registerCommand(Object plugin, PlatformCommand command, String primaryAlias, String... secondaryAliases) {
        if (commandMap.containsKey(primaryAlias.toLowerCase())) {
            return Optional.empty();
        } else {
            CommandMappingImpl mapping = new CommandMappingImpl(command, primaryAlias, ImmutableSet.copyOf(secondaryAliases));
            commandMap.put(primaryAlias.toLowerCase(), mapping);
            return Optional.of(mapping);
        }
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) {
        String[] parts = arguments.split("\\s+", 2);
        String command = parts[0];

        if(command.trim().isEmpty()){
            String response = "Commands: " + this.commandMap.keySet().toString();
            source.sendMessage(this.textFactory.getText(response));
            return resultFactory.empty();
        }

        FoxCommandMapping mapping = commandMap.get(command.toLowerCase());
        if (mapping == null) {
            source.sendMessage(textFactory.getText("No such command: " + command));
            return resultFactory.failure();
        }
        PlatformCommand foxCommand = mapping.getCallable();
        String args = parts.length > 1 ? parts[1] : "";
        try {
            foxCommand.process(source, args);
        } catch (Exception e) {
            logger.debug("Exception processing command: " + arguments, e);
            source.sendMessage(this.textFactory.getText("Error executing commmand: " + e.getMessage()));
        }
        return resultFactory.empty();
    }

    @Override
    public Optional<FoxCommandMapping> registerCommand(Object plugin, FoxStandardCommand command, String primaryAlias, String... secondaryAliases) {
        return this.registerCommand(plugin, (PlatformCommand) command, primaryAlias, secondaryAliases);
    }

    private static class CommandMappingImpl implements FoxCommandMapping{

        final PlatformCommand callable;
        final String primary;
        final Set<String> secondary;

        transient final Set<String> all;

        CommandMappingImpl(PlatformCommand callable, String primary, Set<String> secondary){
            this.callable = callable;
            this.primary = primary;
            this.secondary = ImmutableSet.copyOf(secondary);
            ImmutableSet.Builder<String> builder = ImmutableSet.builder();
            builder.add(primary);
            builder.addAll(secondary);
            this.all = builder.build();
        }

        @Override
        public String getPrimaryAlias() {
            return this.primary;
        }

        @Override
        public Set<String> getAllAliases() {
            return this.all;
        }

        @Override
        public PlatformCommand getCallable() {
            return this.callable;
        }
    }
}
