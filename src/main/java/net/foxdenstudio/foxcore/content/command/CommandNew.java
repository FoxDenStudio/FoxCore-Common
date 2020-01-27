package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.generator.GeneratorCommand;
import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxcore.api.object.index.WritableNamespace;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.FoxPathExt;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class CommandNew extends FoxStandardCommandBase {

    private final FoxPathFactory pathFactory;
    private final FoxMainIndex mainIndex;

    @Inject
    private CommandNew(FoxPathFactory pathFactory, FoxMainIndex mainIndex) {
        this.pathFactory = pathFactory;
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
        String pathStr = args[0];
        FoxPath objectPath = this.pathFactory.fromChecked(pathStr);
        FoxObjectIndex objectIndex = mainIndex.getDefaultObjectIndex();

        FoxPathExt objectPathExt = (FoxPathExt) objectPath;
        Optional<StandardPathComponent> objectPathObjectComponent = objectPathExt.getObjectComponent();
        if (!objectPathObjectComponent.isPresent()) throw new FoxCommandException("No object path specified!");

        Optional<?> opt = objectIndex.getObject(objectPathObjectComponent.get());
        if (opt.isPresent()) throw new FoxCommandException("An object already exists at this path!");

        if (args.length < 2) throw new FoxCommandException("Must specify a generator!");

        String generatorPathStr = args[1];
        String fullGeneratorPathStr = "gen/" + generatorPathStr;
        FoxPath generatorPath = this.pathFactory.from(fullGeneratorPathStr);

        FoxPathExt generatorPathExt = (FoxPathExt) generatorPath;
        Optional<StandardPathComponent> generatorPathObjectComponent = generatorPathExt.getObjectComponent();

        if (!generatorPathObjectComponent.isPresent())
            throw new FoxCommandException("Not a valid generator path specification!");

        Optional<? extends FoxObject> genOpt = objectIndex.getObject(generatorPathObjectComponent.get());
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
        } catch (FoxCommandException e) {
            throw e;
        } catch (Exception e) {
            throw new FoxCommandException("Could not create object due to exception: " + e.getMessage(), e);
        }
        if (newObject.getIndexReference().isPresent())
            throw new FoxCommandException("Generator returned existing object for an unknown reason!");

        ((WritableNamespace) objectIndex).addObject(newObject, objectPathObjectComponent.get());
        source.sendMessage(this.tf.of("Successfully created object!"));

        return resultFactory.success();
    }

    @Override
    public List<String> getSuggestions(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {


        return super.getSuggestions(source, arguments);
    }
}
