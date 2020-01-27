package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.FoxDetailableObject;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.FoxPathExt;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.text.Text;
import net.foxdenstudio.foxcore.platform.text.format.TextColors;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Optional;

public class CommandDetail extends FoxStandardCommandBase {

    private final FoxPathFactory pathFactory;
    private final FoxMainIndex mainIndex;

    private final TextColors textColors;

    @Inject
    private CommandDetail(FoxPathFactory pathFactory, FoxMainIndex mainIndex, TextColors textColors) {
        this.pathFactory = pathFactory;
        this.mainIndex = mainIndex;
        this.textColors = textColors;
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
        FoxPathExt objectPathExt = ((FoxPathExt) objectPath);
        Optional<StandardPathComponent> objectPathObjectCompoment = objectPathExt.getObjectComponent();
        if (!objectPathObjectCompoment.isPresent())
            throw new FoxCommandException("No object path specified!");

        FoxObjectIndex objectIndex = mainIndex.getDefaultObjectIndex();

        Optional<FoxObject> opt = objectIndex.getObject(objectPathObjectCompoment.get());
        if (!opt.isPresent()) {
            throw new FoxCommandException("No object exists at this path!");
        }
        FoxObject object = opt.get();
        FoxArchetype archetype = object.getArchetype();


        Text.Builder builder = tf.builder();
        builder.append(tf.of(
                "\n--------------------------------------",
                textColors.GOLD, "\nInfo about object: ",
                textColors.AQUA, objectPathStr.toLowerCase(),
                textColors.GREEN, "\nArchetype: ",
                textColors.RESET, archetype.getName() + " (" + archetype.getType() + ")"
        ));
        if (object instanceof FoxDetailableObject) {
            String detArgs = args.length > 1 ? args[1] : "";
            builder.append(tf.of(textColors.GOLD, "\n--- Extra Details ---\n"));
            builder.append(((FoxDetailableObject) object).details(source, detArgs));
        }
        source.sendMessage(builder.build());

        return this.resultFactory.success();
    }

}
