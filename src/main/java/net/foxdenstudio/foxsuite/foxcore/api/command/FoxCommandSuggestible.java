package net.foxdenstudio.foxsuite.foxcore.api.command;

import net.foxdenstudio.foxsuite.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;
import java.util.List;

public interface FoxCommandSuggestible extends FoxCommand {
    List<String> getSuggestions(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException;
}
