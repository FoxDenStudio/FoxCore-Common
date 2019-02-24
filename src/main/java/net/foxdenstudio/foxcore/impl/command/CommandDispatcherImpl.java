package net.foxdenstudio.foxcore.impl.command;

import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.api.command.FoxCommandBase;
import net.foxdenstudio.foxcore.api.command.FoxCommandDispatcher;
import net.foxdenstudio.foxcore.api.command.FoxStandardCommand;
import net.foxdenstudio.foxcore.api.command.result.CommandResult;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.platform.command.CommandSource;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class CommandDispatcherImpl extends FoxCommandBase implements FoxCommandDispatcher {

    @FoxLogger("command.dispatcher")
    Logger logger;

    private final Map<String, FoxStandardCommand> commandMap = new HashMap<>();

    @Inject
    protected CommandDispatcherImpl() {
    }

    @Override
    public boolean registerCommand(Object plugin, FoxStandardCommand command, String name) {
        if (commandMap.containsKey(name.toLowerCase())) {
            return false;
        } else {
            commandMap.put(name.toLowerCase(), command);
            return true;
        }
    }

    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) {
        String[] parts = arguments.split("\\s+", 2);
        String command = parts[0];
        FoxStandardCommand foxCommand = commandMap.get(command.toLowerCase());
        if (foxCommand == null) {
            source.sendMessage(textFactory.getText("No such command: " + command));
            return resultFactory.failure();
        }
        String args = parts.length > 1 ? parts[1] : "";
        try {
            foxCommand.process(source, args);
        } catch (FoxCommandException e) {
            logger.debug("Exception processing command: " + arguments, e);
            source.sendMessage(this.textFactory.getText("Error executing commmand: " + e.getMessage()));
        }
        return resultFactory.empty();
    }
}
