package net.foxdenstudio.foxcore.platform.command;

import net.foxdenstudio.foxcore.platform.command.result.PlatformCommandResult;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;
import java.util.List;

public interface PlatformCommand {

    PlatformCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws Exception;

    List<String> getSuggestions(@Nonnull CommandSource source, @Nonnull String arguments) throws Exception;
}
