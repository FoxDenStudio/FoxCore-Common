package net.foxdenstudio.foxcore.standalone.command;

import net.foxdenstudio.foxcore.api.command.FoxCommandBase;
import net.foxdenstudio.foxcore.api.command.result.CommandResult;
import net.foxdenstudio.foxcore.api.command.FoxCommandManager;
import net.foxdenstudio.foxcore.api.command.FoxStandardCommand;
import net.foxdenstudio.foxcore.api.command.result.ResultFactory;
import net.foxdenstudio.foxcore.platform.command.CommandSource;
import net.foxdenstudio.foxcore.api.text.TextFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class SimpleFoxCommandManager extends FoxCommandBase implements FoxCommandManager {

    private final Map<String, FoxStandardCommand> commandMap = new HashMap<>();

    @Inject
    public SimpleFoxCommandManager(TextFactory textFactory, ResultFactory resultFactory) {
        super(textFactory, resultFactory);
    }

    @Override
    public boolean registerCommand(FoxStandardCommand command, String name) {
        if (commandMap.containsKey(name.toLowerCase())) {
            return false;
        } else {
            commandMap.put(name.toLowerCase(), command);
            return true;
        }
    }

    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) {
        String[] parts = arguments.split(" +", 2);
        String command = parts[0];
        FoxStandardCommand foxCommand = commandMap.get(command.toLowerCase());
        if(foxCommand == null) {
            source.sendMessage(textFactory.getText("No such command: " + command));
            return resultFactory.failure();
        }
        String args = parts.length > 1 ? parts[1] : "";
        foxCommand.process(source, args);
        return resultFactory.empty();
    }
}
