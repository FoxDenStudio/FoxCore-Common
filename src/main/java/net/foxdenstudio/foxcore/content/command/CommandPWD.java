package net.foxdenstudio.foxcore.content.command;

import com.google.inject.Inject;
import net.foxdenstudio.foxcore.api.command.context.CommandContext;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.text.format.TextColors;

import javax.annotation.Nonnull;

public class CommandPWD extends FoxStandardCommandBase {

    private final TextColors textColors;

    @Inject
    private CommandPWD(TextColors textColors) {
        this.textColors = textColors;
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        CommandContext context = this.commandContextManager.getCommandContext(source);
        source.sendMessage(tf.of(context.getCurrentPath()));
        return resultFactory.empty();
    }

}
