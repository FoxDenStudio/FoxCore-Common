package net.foxdenstudio.foxsuite.foxcore;

import com.google.common.collect.ImmutableMap;
import net.foxdenstudio.foxsuite.foxcore.api.FoxRegistry;
import net.foxdenstudio.foxsuite.foxcore.api.annotation.FoxCorePluginInstance;
import net.foxdenstudio.foxsuite.foxcore.api.annotation.command.FoxMainDispatcher;
import net.foxdenstudio.foxsuite.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxsuite.foxcore.api.command.standard.FoxCommandDispatcher;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.types.StorageIndex;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxsuite.foxcore.api.world.FoxWorldManager;
import net.foxdenstudio.foxsuite.foxcore.content.region.QubeRegion;
import net.foxdenstudio.foxsuite.foxcore.platform.command.PlatformCommandManager;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.ConsoleSource;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class FoxCore {

    private static final String REGION_PATH = "region";

    private final Provider<PlatformCommandManager> commandManager;
    private final FoxCommandDispatcher mainCommandDispatcher;

    // TODO You don't always have the console source, especially when you're running from the client.
    private final Provider<ConsoleSource> consoleSource;

    private final FoxMainIndex mainIndex;

    private final FoxRegistry registry;

    private final FCStaticContent content;

    private final FoxWorldManager worldManager;

    @FoxLogger("core.greeting")
    private Logger logger;

    @com.google.inject.Inject(optional = true)
    @FoxCorePluginInstance
    Object foxCorePlugin = this;

    @Inject
    public FoxCore(
            Provider<PlatformCommandManager> commandManager,
            @FoxMainDispatcher FoxCommandDispatcher mainCommandDispatcher,
            Provider<ConsoleSource> consoleSource, FoxMainIndex mainIndex,
            FoxRegistry registry, FCStaticContent content, FoxWorldManager worldManager) {
        this.commandManager = commandManager;
        this.mainCommandDispatcher = mainCommandDispatcher;
        this.consoleSource = consoleSource;
        this.mainIndex = mainIndex;
        this.registry = registry;
        this.content = content;
        this.worldManager = worldManager;
    }

    /**
     * Awoo, because I'm furry trash.
     */
    public void awoo() {
        logger.info("Yip purr I'm a library fox!");
    }

    public void configureCommands() {
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandCD, "cd");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandPWD, "pwd");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandList, "list", "ls");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandNew, "new", "create", "make", "mk6");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandDelete, "delete", "del", "remove", "rm");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandDetail, "detail", "det");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandLink, "link");

        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandSave, "save");

        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandEcho, "echo");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandPath, "path");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandStringChar, "strc");
    }

    public void registerCommands() {
        this.commandManager.get().registerCommand(this.foxCorePlugin, this.mainCommandDispatcher, "fox");
    }

    public void setupStaticContent() {
        try {
            this.registry.registerArchetype(content.representationArchetype);
            this.registry.registerArchetype(content.generatorArchetype);
            this.registry.registerArchetype(content.worldArchetype);
            this.registry.simpleMajorTypeRegistration(content.qubeRegionType, QubeRegion.class, QubeRegion.Data.class, ImmutableMap.of(
                    StandardPathComponent.of(REGION_PATH, "rect"), content.generatorRegionRect,
                    StandardPathComponent.of(REGION_PATH, "box"), content.generatorRegionBox,
                    StandardPathComponent.of(REGION_PATH, "flard"), content.generatorRegionFlard
            ));
        } catch (Exception e) {
            logger.error("Exception configuring registry!", e);
        }
    }

    public void loadIndexObjects() {
        for (FoxObjectIndex index : this.mainIndex.getIndices().values()) {
            if (index instanceof StorageIndex) ((StorageIndex) index).load();
        }
    }

    public void loadWorldData() {
        this.worldManager.load();
    }


    public PlatformCommandManager getCommandManager() {
        return commandManager.get();
    }

    public ConsoleSource getConsoleSource() {
        return consoleSource.get();
    }

    public FoxWorldManager getWorldManager() {
        return worldManager;
    }
}
