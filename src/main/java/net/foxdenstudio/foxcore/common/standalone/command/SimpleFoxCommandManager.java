package net.foxdenstudio.foxcore.common.standalone.command;

import net.foxdenstudio.foxcore.common.command.FoxCommand;
import net.foxdenstudio.foxcore.common.command.FoxCommandManager;
import net.foxdenstudio.foxcore.common.platform.command.CommandSource;
import net.foxdenstudio.foxcore.common.text.TextMaker;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class SimpleFoxCommandManager implements FoxCommandManager {

    private final TextMaker textMaker;

    private final Map<String, FoxCommand> commandMap = new HashMap<>();

    @Inject
    public SimpleFoxCommandManager(TextMaker textMaker) {
        this.textMaker = textMaker;
    }

    @Override
    public boolean registerCommand(FoxCommand command, String name) {
        if (commandMap.containsKey(name.toLowerCase())) {
            return false;
        } else {
            commandMap.put(name.toLowerCase(), command);
            return true;
        }
    }

    @Override
    public boolean process(@Nonnull CommandSource source, @Nonnull String arguments) {
        String[] parts = arguments.split(" +", 2);
        String command = parts[0];
        FoxCommand foxCommand = commandMap.get(command.toLowerCase());
        if(foxCommand == null) {
            source.sendMessage(textMaker.getText("No such command: " + command));
            return false;
        }
        String args = parts.length > 1 ? parts[1] : "";
        foxCommand.process(source, args);
        return true;
    }
}
