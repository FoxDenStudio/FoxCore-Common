package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.platform.command.CommandSource;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class CommandNew extends FoxStandardCommandBase {

    @Inject
    private CommandNew() {
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) {


        return resultFactory.empty();
    }

}
