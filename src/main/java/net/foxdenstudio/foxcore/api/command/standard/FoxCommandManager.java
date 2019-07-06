package net.foxdenstudio.foxcore.api.command.standard;

import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.platform.command.CommandSource;

import javax.annotation.Nonnull;

public interface FoxCommandManager extends FoxCommandDispatcher {

    @Override
    FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments);

}
