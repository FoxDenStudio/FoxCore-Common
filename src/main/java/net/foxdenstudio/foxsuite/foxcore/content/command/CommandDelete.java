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
            builder.append(tf.of("Sucessfully removed object \"", reference.getPrimePath().orElse(null), "\" from index!"));
        } else {
            builder.append(tf.of("Object already removed somehow. Success?"));
        }
        source.sendMessage(builder.build());
        return resultFactory.empty();
    }
}
