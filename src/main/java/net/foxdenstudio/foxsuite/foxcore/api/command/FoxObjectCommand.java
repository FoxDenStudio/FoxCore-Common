package net.foxdenstudio.foxsuite.foxcore.api.command;

import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;

public interface FoxObjectCommand {

    FoxCommandResult process(@Nonnull CommandSource source, @Nonnull FoxObject object, @Nonnull String arguments);

}
