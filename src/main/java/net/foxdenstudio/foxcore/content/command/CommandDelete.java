package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.command.context.CommandContext;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;
import net.foxdenstudio.foxcore.api.object.reference.WritableIndexReference;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.text.Text;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class CommandDelete extends FoxStandardCommandBase {

    private final FoxPathFactory pathFactory;

    @Inject
    private CommandDelete(FoxPathFactory pathFactory) {
        this.pathFactory = pathFactory;
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        arguments = arguments.trim();
        if (arguments.isEmpty()) {
            source.sendMessage(tf.of("Syntax: <name/path>"));
            return resultFactory.empty();
        }
        String[] args = arguments.split(" +", 2);
        String objectPathStr = args[0];
        FoxPath objectPath = this.pathFactory.fromChecked(objectPathStr);
        CommandContext context = this.commandContextManager.getCommandContext(source);

        IndexReference reference = context.getObjectFromIndex(objectPath);
        if (!(reference instanceof WritableIndexReference))
            throw new FoxCommandException("Object \"" + reference + "\" is read-only!");
        boolean success = ((WritableIndexReference) reference).removeObjectFromIndex();
        Text.Builder builder = tf.builder();
        if (success) {
            builder.append(tf.of("Sucessfully removed object \"", reference.getPrimaryPath().orElse(null), "\" from index!"));
        } else {
            builder.append(tf.of("Object already removed somehow. Success?"));
        }
        source.sendMessage(builder.build());
        return resultFactory.empty();
    }
}
