package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxcore.api.object.index.types.StorageIndex;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class CommandSave extends FoxStandardCommandBase {

    private final FoxMainIndex mainIndex;

    @FoxLogger
    Logger logger;

    @Inject
    private CommandSave(FoxMainIndex mainIndex) {
        this.mainIndex = mainIndex;
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        if (!source.hasPermission("fox.save")) return resultFactory.failure();
        logger.info("Source \"" + source.getName() + "\" started index save.");

        for (FoxObjectIndex index : this.mainIndex.getIndices().values()){
            if(index instanceof StorageIndex){
                ((StorageIndex) index).save();
            }
        }
        logger.info("Finished saving indices.");

        String message = "Saved successfully!";
        source.sendMessage(tf.of(message));
        return resultFactory.empty();
    }

}
