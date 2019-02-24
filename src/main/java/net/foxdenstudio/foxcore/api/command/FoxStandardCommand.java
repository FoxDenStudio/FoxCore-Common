package net.foxdenstudio.foxcore.api.command;

import net.foxdenstudio.foxcore.api.command.result.CommandResult;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.platform.command.PlatformCommand;
import net.foxdenstudio.foxcore.platform.command.CommandSource;

import javax.annotation.Nonnull;

public interface FoxStandardCommand extends FoxCommand, PlatformCommand {

    CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException;

}
