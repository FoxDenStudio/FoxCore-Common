package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.platform.command.CommandSource;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class CommandEcho extends FoxStandardCommandBase {

    @Inject
    public CommandEcho() {
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) {
        if (!source.hasPermission("fox.echo")) return resultFactory.failure();
        String message = source.getName() + " said: "
                + (arguments.isEmpty() ? "(empty)" : arguments);
        source.sendMessage(textFactory.getText(message));
        return resultFactory.empty();
    }
}
