package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.platform.command.CommandSource;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class CommandList extends FoxStandardCommandBase {

    @Inject
    private CommandList(){

    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        source.sendMessage(this.textFactory.getText("Here's a list: yip, bark, awoo!"));
        return this.resultFactory.empty();
    }

}
