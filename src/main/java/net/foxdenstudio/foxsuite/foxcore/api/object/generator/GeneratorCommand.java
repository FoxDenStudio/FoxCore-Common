package net.foxdenstudio.foxsuite.foxcore.api.object.generator;

import net.foxdenstudio.foxsuite.foxcore.api.command.FoxCommand;
import net.foxdenstudio.foxsuite.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;

public interface GeneratorCommand<G extends FoxObject> extends FoxCommand {

    G generate(CommandSource source, String arguments) throws FoxCommandException;

}
