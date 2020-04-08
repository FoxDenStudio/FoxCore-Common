package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.command.context.CommandContext;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.generator.GeneratorCommand;
import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxcore.api.object.index.Namespace;
import net.foxdenstudio.foxcore.api.object.index.WritableNamespace;
import net.foxdenstudio.foxcore.api.object.reference.FoxObjectReference;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.FoxPathExt;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.path.section.ObjectPathSection;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class CommandNew extends FoxStandardCommandBase {

    private final FoxPathFactory pathFactory;
    private final FoxMainIndex mainIndex;

    private FoxPath generatorsPath;

    @Inject
    private CommandNew(FoxPathFactory pathFactory, FoxMainIndex mainIndex) {
        this.pathFactory = pathFactory;
        this.mainIndex = mainIndex;
        this.generatorsPath = pathFactory.from("@mem:gen");
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        arguments = arguments.trim();
        if (arguments.isEmpty()) {
            source.sendMessage(tf.of("Syntax: <name/path> <generator> <arguments>"));
            return resultFactory.empty();
        }
        CommandContext context = this.commandContextManager.getCommandContext(source);

        String[] args = arguments.split(" +", 3);
        String pathStr = args[0];
        FoxPath objectPath = this.pathFactory.fromChecked(pathStr);
        FoxPath completePath = context.getCurrentPath().resolve(objectPath);
        Namespace namespace = context.getNamespaceDirect(completePath);

        WritableNamespace destination;
        if(namespace instanceof WritableNamespace){
            destination = ((WritableNamespace) namespace);
        } else {
            throw new FoxCommandException("Namespace \"" + namespace.getIndexPath() + "\" is read-only!");
        }

        FoxPathExt objectPathExt = (FoxPathExt) completePath;
        Optional<ObjectPathSection> objectPathObjectSectionOpt = objectPathExt.getObjectSection();
        if (!objectPathObjectSectionOpt.isPresent()) throw new FoxCommandException("No object path specified!");
        ObjectPathSection objectPathObjectSection = objectPathObjectSectionOpt.get();
        if (!destination.isPathValid(objectPathObjectSection, true))
            throw new FoxCommandException("The object path \"" + objectPathObjectSection + "\" is not valid in this namespace!");

        Optional<?> opt = destination.getObject(objectPathObjectSection);
        if (opt.isPresent()) throw new FoxCommandException("An object already exists at this path!");

        if (args.length < 2) throw new FoxCommandException("Must specify a generator!");

        String generatorPathStr = args[1];;
        FoxPath generatorPath = this.generatorsPath.resolve(this.pathFactory.from(generatorPathStr));

        FoxPathExt generatorPathExt = (FoxPathExt) generatorPath;
        Namespace generatorNamespace = context.getNamespaceDirect(generatorPath);

        Optional<ObjectPathSection> generatorObjectSection = generatorPathExt.getObjectSection();

        if (!generatorObjectSection.isPresent())
            throw new FoxCommandException("Not a valid generator path specification!");

        Optional<FoxObject> genOpt = generatorNamespace.getObject(generatorObjectSection.get());
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

        destination.addObject(newObject, objectPathObjectSectionOpt.get());
        source.sendMessage(this.tf.of("Successfully created object!"));

        return resultFactory.success();
    }

    @Override
    public List<String> getSuggestions(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {


        return super.getSuggestions(source, arguments);
    }
}
