package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;

public class CommandLink extends FoxStandardCommandBase {

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        return null;
    }

}
