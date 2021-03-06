package net.foxdenstudio.foxsuite.foxcore.api.object.generator;

import net.foxdenstudio.foxsuite.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;

public interface RegeneratorCommand<G extends FoxObject> extends GeneratorCommand<G> {

    @Override
    G generate(CommandSource source, String arguments) throws FoxCommandException;

    void populate(G g, CommandSource source, String arguments) throws FoxCommandException;
}
