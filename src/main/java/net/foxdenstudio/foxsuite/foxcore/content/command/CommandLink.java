package net.foxdenstudio.foxsuite.foxcore.content.command;

import net.foxdenstudio.foxsuite.foxcore.api.command.context.CommandContext;
import net.foxdenstudio.foxsuite.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxsuite.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxsuite.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.IndexReference;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class CommandLink extends FoxStandardCommandBase {

    private final FoxPathFactory pathFactory;

    @Inject
    private CommandLink(FoxPathFactory pathFactory) {
        this.pathFactory = pathFactory;
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {

        arguments = arguments.trim();
        if (arguments.isEmpty()) {
            source.sendMessage(tf.of("Syntax: link <source> <target>"));
            return resultFactory.empty();
        }
        String[] args = arguments.split(" +", 3);
        if (args.length < 2) throw new FoxCommandException("No link target specified.");

        String sourcePathStr = args[0];
        String targetPathStr = args[1];


        FoxPath sourcePath = this.pathFactory.fromChecked(sourcePathStr);
        FoxPath targetPath = this.pathFactory.fromChecked(targetPathStr);

        CommandContext context = this.commandContextManager.getCommandContext(source);

        IndexReference sourceRef = context.getObjectFromIndex(sourcePath);
        FoxObject sourceObj = sourceRef.getObject().orElse(null);

        IndexReference targetRef = context.getObjectFromIndex(targetPath);
        FoxObject targetObj = targetRef.getObject().orElse(null);

        if (sourceObj == null || targetObj == null) throw new FoxCommandException("Object missing for reference!?!");

        sourceObj.link(targetObj, null);

        source.sendMessage(tf.of("You did it!"));

        return resultFactory.empty();
    }

}
