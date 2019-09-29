package net.foxdenstudio.foxcore.api.object.generator;

import net.foxdenstudio.foxcore.api.command.FoxCommand;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;

import java.util.Optional;

public interface GeneratorObject<G extends FoxObject> extends FoxObject, GeneratorCommand<G> {

}