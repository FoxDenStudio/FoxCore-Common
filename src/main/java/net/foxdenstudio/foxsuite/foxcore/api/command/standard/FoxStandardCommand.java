package net.foxdenstudio.foxsuite.foxcore.api.command.standard;

import net.foxdenstudio.foxsuite.foxcore.api.command.FoxCommandSuggestible;
import net.foxdenstudio.foxsuite.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxsuite.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxsuite.foxcore.platform.command.PlatformCommand;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;

public interface FoxStandardCommand extends FoxCommandSuggestible, PlatformCommand {

    FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException;

}
