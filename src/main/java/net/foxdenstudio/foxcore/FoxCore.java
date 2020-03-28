package net.foxdenstudio.foxcore;

import net.foxdenstudio.foxcore.api.FoxRegistry;
import net.foxdenstudio.foxcore.api.annotation.FoxCorePluginInstance;
import net.foxdenstudio.foxcore.api.annotation.command.FoxMainDispatcher;
import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.api.command.standard.FoxCommandDispatcher;
import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.object.index.WritableIndex;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.path.section.ObjectPathSection;
import net.foxdenstudio.foxcore.platform.command.PlatformCommandManager;
import net.foxdenstudio.foxcore.platform.command.source.ConsoleSource;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class FoxCore {

    private final Provider<PlatformCommandManager> commandManager;
    private final FoxCommandDispatcher mainCommandDispatcher;
    private final ConsoleSource consoleSource;

    private final FoxMainIndex mainIndex;

    private final FoxRegistry registry;

    private final StaticContent content;

    @FoxLogger("greeting")
    private Logger logger;

    @com.google.inject.Inject(optional = true)
    @FoxCorePluginInstance
    Object foxCorePlugin = this;

    @Inject
    public FoxCore(
            Provider<PlatformCommandManager> commandManager,
            @FoxMainDispatcher FoxCommandDispatcher mainCommandDispatcher,
            ConsoleSource consoleSource, FoxMainIndex mainIndex,
            FoxRegistry registry, StaticContent content) {
        this.commandManager = commandManager;
        this.mainCommandDispatcher = mainCommandDispatcher;
        this.consoleSource = consoleSource;
        this.mainIndex = mainIndex;
        this.registry = registry;
        this.content = content;
    }

    /**
     * Awoo, because I'm furry trash.
     */
    public void awoo() {
        logger.info("Awoo!");
    }

    public void configureCommands() {
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandEcho, "echo");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandPath, "path");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandList, "list");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandNew, "new");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandDetail, "detail", "det");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandPWD, "pwd");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandCD, "cd");
    }

    public void registerCommands() {
        this.commandManager.get().registerCommand(this.foxCorePlugin, this.mainCommandDispatcher, "fox");
    }

    public void setupStaticContent() {
        WritableIndex writable = mainIndex.getDefaultObjectIndex();
        writable.addObject(content.generatorRegionRect, ObjectPathSection.of("gen", "region", "rect"));
        writable.addObject(content.generatorRegionBox, ObjectPathSection.of("gen", "region", "box"));
        writable.addObject(content.generatorRegionFlard, ObjectPathSection.of("gen", "region", "flard"));
        try {
            this.registry.registerArchetype(content.qubeRegionType);
            this.registry.registerArchetype(content.representationArchetype);
            this.registry.registerArchetype(content.generatorArchetype);
        } catch (Exception e) {
            logger.error("Exception configuring registry!", e);
        }
    }

    public PlatformCommandManager getCommandManager() {
        return commandManager.get();
    }

    public ConsoleSource getConsoleSource() {
        return consoleSource;
    }

}
