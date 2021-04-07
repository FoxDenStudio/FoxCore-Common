package net.foxdenstudio.foxsuite.foxcore.content.command;

import com.google.inject.Inject;
import net.foxdenstudio.foxsuite.foxcore.api.command.context.CommandContext;
import net.foxdenstudio.foxsuite.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxsuite.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxsuite.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxsuite.foxcore.platform.text.format.TextColors;

import javax.annotation.Nonnull;

public class CommandCD extends FoxStandardCommandBase {

    private final TextColors textColors;
    private final FoxPathFactory pathFactory;

    @Inject
    private CommandCD(TextColors textColors, FoxPathFactory pathFactory) {
        this.textColors = textColors;
        this.pathFactory = pathFactory;
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        CommandContext context = this.commandContextManager.getCommandContext(source);

        arguments = arguments.trim();
        if(arguments.isEmpty()) {
            context.resetPath();
        } else {
            FoxPath path = pathFactory.fromChecked(arguments);
            context.changePath(path);
        }

        source.sendMessage(tf.of(context.getCurrentPath()));
        return resultFactory.empty();
    }
}
