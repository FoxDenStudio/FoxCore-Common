package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.command.FoxCommandBase;
import net.foxdenstudio.foxcore.api.command.FoxStandardCommand;
import net.foxdenstudio.foxcore.api.command.result.CommandResult;
import net.foxdenstudio.foxcore.api.command.result.ResultFactory;
import net.foxdenstudio.foxcore.platform.command.CommandSource;
import net.foxdenstudio.foxcore.api.text.TextFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class CommandNew extends FoxCommandBase implements FoxStandardCommand {

    @Inject
    public CommandNew(TextFactory textFactory, ResultFactory resultFactory) {
        super(textFactory, resultFactory);
    }

    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) {


        return resultFactory.empty();
    }

}
