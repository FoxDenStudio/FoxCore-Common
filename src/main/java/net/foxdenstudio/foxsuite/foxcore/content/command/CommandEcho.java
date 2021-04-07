package net.foxdenstudio.foxsuite.foxcore.content.command;

import net.foxdenstudio.foxsuite.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxsuite.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class CommandEcho extends FoxStandardCommandBase {

    @Inject
    private CommandEcho() {
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) {
        if (!source.hasPermission("fox.echo")) return resultFactory.failure();
        String message = source.getName() + " said: "
                + (arguments.isEmpty() ? "(empty)" : arguments);
        source.sendMessage(tf.of(message));
        return resultFactory.empty();
    }
}
