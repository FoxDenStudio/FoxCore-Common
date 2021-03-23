package net.foxdenstudio.foxcore.api.command.standard;

import net.foxdenstudio.foxcore.api.command.FoxCommandSuggestible;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.platform.command.PlatformCommand;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;

public interface FoxStandardCommand extends FoxCommandSuggestible, PlatformCommand {

    FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException;

}
