package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.generator.GeneratorCommand;
import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxcore.api.object.index.WritableNamespace;
import net.foxdenstudio.foxcore.api.path.components.FoxObjectPath;
import net.foxdenstudio.foxcore.api.path.factory.FoxObjectPathFactory;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class CommandNew extends FoxStandardCommandBase {

    private final FoxObjectPathFactory objectPathFactory;
    private final FoxMainIndex mainIndex;

    @Inject
    private CommandNew(FoxObjectPathFactory objectPathFactory, FoxMainIndex mainIndex) {
        this.objectPathFactory = objectPathFactory;
        this.mainIndex = mainIndex;
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        arguments = arguments.trim();
        if (arguments.isEmpty()) {
            source.sendMessage(tf.of("Syntax: <name/path> <generator> <arguments>"));
            return resultFactory.empty();
        }
        String[] args = arguments.split(" +", 3);
        String objectPathStr = args[0];
        FoxObjectPath objectPath = this.objectPathFactory.getPath(objectPathStr);
        FoxObjectIndex objectIndex = mainIndex.getDefaultObjectIndex();

        if (!(objectIndex instanceof WritableNamespace))
            throw new FoxCommandException("This namespace is not writable!");

        Optional<?> opt = objectIndex.getObject(objectPath);
        if (opt.isPresent()) throw new FoxCommandException("An object already exists at this path!");

        if (args.length < 2) throw new FoxCommandException("Must specify a generator!");

        String generatorPathStr = args[1];
        String fullGeneratorPathStr = "gen/" + generatorPathStr;
        FoxObjectPath generatorPath = this.objectPathFactory.getPath(fullGeneratorPathStr);

        Optional<? extends FoxObject> genOpt = objectIndex.getObject(generatorPath);
        if (!genOpt.isPresent())
            throw new FoxCommandException("No generator exists with name \"" + generatorPathStr + "\"!");

        FoxObject generator = genOpt.get();
        if (!(generator instanceof GeneratorCommand<?>))
            throw new FoxCommandException("\"" + generatorPathStr + "\" is not an object generator!");

        GeneratorCommand<?> generatorCommand = ((GeneratorCommand<?>) generator);

        String generatorArguments = args.length > 2 ? args[2] : "";

        FoxObject newObject;
        try {
            newObject = generatorCommand.generate(source, generatorArguments);
        } catch (FoxCommandException e){
            throw e;
        } catch (Exception e){
            throw new FoxCommandException("Could not create object due to exception: " + e.getMessage(), e);
        }
        if (newObject.getIndexReference().isPresent())
            throw new FoxCommandException("Generator returned existing object for an unknown reason!");

        ((WritableNamespace) objectIndex).addObject(newObject, objectPath);
        source.sendMessage(this.tf.of("Successfully created object!"));

        return resultFactory.success();
    }

    @Override
    public List<String> getSuggestions(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {



        return super.getSuggestions(source, arguments);
    }
}
