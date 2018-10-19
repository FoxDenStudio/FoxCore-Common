package net.foxdenstudio.foxcore.common.command;

import net.foxdenstudio.foxcore.common.platform.command.CommandSource;

import javax.annotation.Nonnull;

public interface FoxCommand {

    boolean process(@Nonnull CommandSource source, @Nonnull String arguments);
}
