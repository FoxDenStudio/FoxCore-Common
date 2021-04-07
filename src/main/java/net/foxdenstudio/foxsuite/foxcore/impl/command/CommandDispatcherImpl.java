package net.foxdenstudio.foxsuite.foxcore.impl.command;

import com.google.common.collect.ImmutableSet;
import net.foxdenstudio.foxsuite.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxsuite.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxsuite.foxcore.api.command.standard.FoxCommandDispatcher;
import net.foxdenstudio.foxsuite.foxcore.api.command.standard.FoxCommandMapping;
import net.foxdenstudio.foxsuite.foxcore.api.command.standard.FoxStandardCommand;
import net.foxdenstudio.foxsuite.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxsuite.foxcore.platform.command.PlatformCommand;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.ConsoleSource;
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
    private final Map<String, CommandMappingImpl> effectiveCommandMap = new HashMap<>();

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
            computeMap();
            return Optional.of(mapping);
        }
    }

    private void computeMap(){
        effectiveCommandMap.clear();
        effectiveCommandMap.putAll(commandMap);
        for(Map.Entry<String, CommandMappingImpl> entry: commandMap.entrySet()){
            CommandMappingImpl mapping = entry.getValue();
            for(String alias : mapping.secondary){
                effectiveCommandMap.putIfAbsent(alias, mapping);
            }
        }
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) {
        String[] parts = arguments.split("\\s+", 2);
        String command = parts[0];

        if (command.trim().isEmpty()) {
            String response = "Commands: " + this.commandMap.keySet().toString();
            source.sendMessage(this.tf.of(response));
            return resultFactory.empty();
        }

        FoxCommandMapping mapping = effectiveCommandMap.get(command.toLowerCase());
        if (mapping == null) {
            source.sendMessage(tf.of("No such command: " + command));
            return resultFactory.failure();
        }
        PlatformCommand foxCommand = mapping.getCallable();
        String args = parts.length > 1 ? parts[1] : "";
        try {
            foxCommand.process(source, args);
        } catch (Exception e) {
            source.sendMessage(this.tf.of("Error executing commmand: " + e.getMessage()));
            if (source instanceof ConsoleSource) {
                logger.info("Exception processing command: {}", arguments, e);
            } else {
                logger.debug("Exception processing command: {}", arguments, e);
            }

        }
        return resultFactory.empty();
    }

    @Override
    public Optional<FoxCommandMapping> registerCommand(Object plugin, FoxStandardCommand command, String primaryAlias, String... secondaryAliases) {
        return this.registerCommand(plugin, (PlatformCommand) command, primaryAlias, secondaryAliases);
    }

    private static class CommandMappingImpl implements FoxCommandMapping {

        final PlatformCommand callable;
        final String primary;
        final Set<String> secondary;

        transient final Set<String> all;

        CommandMappingImpl(PlatformCommand callable, String primary, Set<String> secondary) {
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
