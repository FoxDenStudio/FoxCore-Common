package net.foxdenstudio.foxsuite.foxcore.content.command;

import net.foxdenstudio.foxsuite.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxsuite.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxsuite.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxsuite.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.types.StorageIndex;
import net.foxdenstudio.foxsuite.foxcore.api.world.FoxWorldManager;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandSave extends FoxStandardCommandBase {

    private final FoxMainIndex mainIndex;
    private final FoxWorldManager worldManager;

    @FoxLogger("command.save")
    private Logger logger;

    private Map<String, Target> targetMap;

    @Inject
    private CommandSave(FoxMainIndex mainIndex, FoxWorldManager worldManager) {
        this.mainIndex = mainIndex;
        this.worldManager = worldManager;
        this.targetMap = new HashMap<>();
        this.addTarget("all", new Target() {
            @Override
            public void save(CommandSource source, String arg) throws FoxCommandException {
                logger.info("Source \"{}\" started complete save.", source.getName());
                for (Map.Entry<String, Target> entry : CommandSave.this.targetMap.entrySet()) {
                    Target target = entry.getValue();
                    if (target == this) continue;
                    target.save(source, null);
                }
            }
        });
        this.addTarget("index", (source, arg) -> {
            if (arg == null || arg.isEmpty()) {
                logger.info("Source \"{}\" started full index save.", source.getName());
                int counter = 0;
                for (FoxObjectIndex index : this.mainIndex.getIndices().values()) {
                    if (index instanceof StorageIndex) {
                        ((StorageIndex) index).save();
                        counter++;
                    }
                }
                if (logger.isInfoEnabled())
                    logger.info("Source \"{}\" saved {} ind{}.", source.getName(), counter, counter == 1 ? "ex" : "ices");
            } else {
                Optional<FoxObjectIndex> indexOpt = this.mainIndex.getObjectIndex(arg);
                if (indexOpt.isPresent()) {
                    FoxObjectIndex index = indexOpt.get();
                    if (index instanceof StorageIndex) {
                        logger.info("Source \"{}\" started save of index \"{}\".", source.getName(), index.getIndexName());
                        ((StorageIndex) index).save();
                    } else {
                        throw new FoxCommandException("Index \"" + index.getIndexName()
                                + "\" is not a storage index and cannot be saved.");
                    }
                } else {
                    throw new FoxCommandException("There is no index with name \"" + arg + "\"");
                }
            }
        });
        this.addTarget("world", (source, arg) -> {
            logger.info("Source \"{}\" started world index save.", source.getName());
            this.worldManager.save();
        });
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        if (!source.hasPermission("fox.save")) return resultFactory.failure();
        String[] parts = arguments.trim().split(" +", 3);
        String targetStr = null;
        String arg = null;
        Target target = null;
        if (parts.length < 1 || parts[0].isEmpty()) {
            targetStr = "all";
        } else {
            targetStr = parts[0];
            if (parts.length > 1) {
                parts[1] = parts[1].trim();
                if (!parts[1].isEmpty())
                    arg = parts[1];
            }
        }
        target = this.targetMap.get(targetStr.toLowerCase());
        if (target == null)
            throw new FoxCommandException("There is no target with name \"" + targetStr + "\"");

        target.save(source, arg);

        if (logger.isInfoEnabled())
            logger.info("Finished save operation on target \"{}{}\"", targetStr, arg == null ? "" : "\" with argument \"" + arg);

        String message = "Saved successfully!";
        source.sendMessage(tf.of(message));
        return resultFactory.empty();
    }

    public boolean addTarget(String name, Target target) {
        if (this.targetMap.containsKey(name)) return false;
        this.targetMap.put(name.toLowerCase(), target);
        return true;
    }

    public interface Target {
        void save(CommandSource source, String arg) throws FoxCommandException;
    }
}
