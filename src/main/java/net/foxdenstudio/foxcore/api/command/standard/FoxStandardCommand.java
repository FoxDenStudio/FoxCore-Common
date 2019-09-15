package net.foxdenstudio.foxcore.api.command.standard;

import net.foxdenstudio.foxcore.api.command.FoxCommand;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.command.PlatformCommand;

import javax.annotation.Nonnull;
import java.util.List;

public interface FoxStandardCommand extends FoxCommand, PlatformCommand {

    FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException;

    List<String> getSuggestions(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException;

}
