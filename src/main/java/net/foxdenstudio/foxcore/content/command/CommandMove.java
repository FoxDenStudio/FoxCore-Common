package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.command.context.CommandContext;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.reference.types.IndexReference;
import net.foxdenstudio.foxcore.api.object.reference.types.WritableIndexReference;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.text.Text;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class CommandMove extends FoxStandardCommandBase {

    private final FoxPathFactory pathFactory;

    @Inject
    private CommandMove(FoxPathFactory pathFactory) {
        this.pathFactory = pathFactory;
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        arguments = arguments.trim();
        if (arguments.isEmpty()) {
            source.sendMessage(tf.of("Syntax: <name/path>"));
            return resultFactory.empty();
        }
        String[] args = arguments.split(" +", 3);
        String objectPathStr = args[0];
        FoxPath objectPath = this.pathFactory.fromChecked(objectPathStr);
        CommandContext context = this.commandContextManager.getCommandContext(source);

        IndexReference reference = context.getObjectFromIndex(objectPath);
        if (!(reference instanceof WritableIndexReference))
            throw new FoxCommandException("Object \"" + reference + "\" is read-only!");




        Text.Builder builder = tf.builder();



        source.sendMessage(builder.build());
        return resultFactory.empty();
    }
}
