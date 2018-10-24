package net.foxdenstudio.foxcore.api.command;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.command.result.CommandResult;
import net.foxdenstudio.foxcore.platform.command.CommandSource;

import javax.annotation.Nonnull;

public interface FoxObjectCommand {

    CommandResult process(@Nonnull CommandSource source, @Nonnull FoxObject object, @Nonnull String arguments);

}
