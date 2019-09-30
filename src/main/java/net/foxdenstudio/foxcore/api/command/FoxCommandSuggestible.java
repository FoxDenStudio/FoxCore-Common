package net.foxdenstudio.foxcore.api.command;

import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;
import java.util.List;

public interface FoxCommandSuggestible extends FoxCommand {
    List<String> getSuggestions(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException;
}
