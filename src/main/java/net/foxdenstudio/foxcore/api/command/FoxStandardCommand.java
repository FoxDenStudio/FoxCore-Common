package net.foxdenstudio.foxcore.api.command;

import net.foxdenstudio.foxcore.api.command.result.CommandResult;
import net.foxdenstudio.foxcore.platform.command.CommandSource;

import javax.annotation.Nonnull;

public interface FoxStandardCommand extends FoxCommand {

    CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments);

}