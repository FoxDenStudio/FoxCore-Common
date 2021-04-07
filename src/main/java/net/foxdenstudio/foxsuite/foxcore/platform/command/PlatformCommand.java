package net.foxdenstudio.foxsuite.foxcore.platform.command;

import net.foxdenstudio.foxsuite.foxcore.platform.command.result.PlatformCommandResult;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;
import java.util.List;

public interface PlatformCommand {

    PlatformCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws Exception;

    List<String> getSuggestions(@Nonnull CommandSource source, @Nonnull String arguments) throws Exception;
}
