package net.foxdenstudio.foxsuite.foxcore.content.command;

import net.foxdenstudio.foxsuite.foxcore.api.command.context.CommandContext;
import net.foxdenstudio.foxsuite.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxsuite.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxsuite.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.IndexReference;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.WritableIndexReference;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxsuite.foxcore.platform.text.Text;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class CommandDelete extends FoxStandardCommandBase {

    private final FoxPathFactory pathFactory;

    @Inject
    private CommandDelete(FoxPathFactory pathFactory) {
        this.pathFactory = pathFactory;
    }

    @Override
    public FoxCommandResult process(@Nonnull final CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        arguments = arguments.trim();
        if (arguments.isEmpty()) {
            source.sendMessage(tf.of("Syntax: <name/path>"));
            return resultFactory.empty();
        }
        final String[] args = arguments.split(" +", 2);
        final String objectPathStr = args[0];
        final FoxPath objectPath = this.pathFactory.fromChecked(objectPathStr);
        final CommandContext context = this.commandContextManager.getCommandContext(source);

        final IndexReference reference = context.getObjectFromIndex(objectPath);
        if (!(reference instanceof WritableIndexReference))
            throw new FoxCommandException("Object \"" + reference + "\" is read-only!");

        final FoxPath mainPath =  reference.getPrimePath().orElse(null);

        final boolean success = ((WritableIndexReference) reference).removeObjectFromIndex();

        final Text.Builder builder = tf.builder();
        if (success) {
            builder.append(tf.of("Sucessfully removed object \"", mainPath, "\" from index!"));
        } else {
            builder.append(tf.of("Object already removed somehow. Success?"));
        }
        source.sendMessage(builder.build());
        return resultFactory.empty();
    }
}
