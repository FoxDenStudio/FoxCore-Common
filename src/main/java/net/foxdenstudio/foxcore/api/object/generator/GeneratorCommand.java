package net.foxdenstudio.foxcore.api.object.generator;

import net.foxdenstudio.foxcore.api.command.FoxCommand;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;

public interface GeneratorCommand<G extends FoxObject> extends FoxCommand {

    G generate(CommandSource source, String arguments) throws FoxCommandException;

}
