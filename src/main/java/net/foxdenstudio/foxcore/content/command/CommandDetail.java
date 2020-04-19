package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.attribute.value.FoxAttrValue;
import net.foxdenstudio.foxcore.api.command.context.CommandContext;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.FoxDetailableObject;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.text.Text;
import net.foxdenstudio.foxcore.platform.text.format.TextColors;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Optional;

public class CommandDetail extends FoxStandardCommandBase {

    private final FoxPathFactory pathFactory;

    @Inject
    private CommandDetail(FoxPathFactory pathFactory) {
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
        FoxObject object = reference.getObject().orElse(null);
        if (object == null) throw new FoxCommandException("Object missing for reference!");

        FoxArchetype archetype = object.getArchetype();

        Text.Builder builder = tf.builder();
        builder.append(tf.of(
                "\n--------------------------------------",
                tc.GOLD, "\nInfo about object: ",
                tc.AQUA, objectPathStr.toLowerCase(),
                tc.GREEN, "\nArchetype: ",
                tc.RESET, archetype.getName() + " (" + archetype.getType() + ")",
                tc.AQUA, "\nAttributes:"
        ));
        for(FoxAttribute<?> attribute : object.getAttributes()){

            builder.append(tf.of(tc.GREEN, "\n  ", attribute,
                    tc.RESET, ": ", object.getAttrValueWeak(attribute).orElse(null)));
        }
        if (object instanceof FoxDetailableObject) {
            String detArgs = args.length > 1 ? args[1] : "";
            builder.append(tf.of(tc.GOLD, "\n--- Extra Details ---\n"));
            builder.append(((FoxDetailableObject) object).details(source, detArgs));
        }
        source.sendMessage(builder.build());

        return this.resultFactory.success();
    }

}
