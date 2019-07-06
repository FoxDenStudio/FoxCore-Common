package net.foxdenstudio.foxcore.api.command;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.platform.command.CommandSource;

import javax.annotation.Nonnull;

public interface FoxObjectCommand {

    FoxCommandResult process(@Nonnull CommandSource source, @Nonnull FoxObject object, @Nonnull String arguments);

}
