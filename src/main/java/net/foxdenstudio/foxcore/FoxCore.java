package net.foxdenstudio.foxcore;

import net.foxdenstudio.foxcore.api.annotation.FoxCorePluginInstance;
import net.foxdenstudio.foxcore.api.annotation.command.FoxMainDispatcher;
import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.api.command.standard.FoxCommandDispatcher;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxcore.api.object.index.WritableNamespace;
import net.foxdenstudio.foxcore.api.path.factory.FoxObjectPathFactory;
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
    private final FoxObjectPathFactory objectPathFactory;

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
            ConsoleSource consoleSource,
            FoxMainIndex mainIndex, FoxObjectPathFactory objectPathFactory,
            StaticContent content) {
        this.commandManager = commandManager;
        this.mainCommandDispatcher = mainCommandDispatcher;
        this.consoleSource = consoleSource;
        this.mainIndex = mainIndex;
        this.objectPathFactory = objectPathFactory;
        this.content = content;
    }

    public void awoo() {
        logger.info("Awoo!");
    }

    public void configureCommands() {
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandEcho, "echo");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandPath, "path");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, content.commandList, "list");
    }

    public void registerCommands() {
        this.commandManager.get().registerCommand(this.foxCorePlugin, this.mainCommandDispatcher, "fox");
    }

    public void setupStaticContent() {
        FoxObjectIndex objectIndex = mainIndex.getDefaultObjectIndex();
        try {
            WritableNamespace writable = ((WritableNamespace) objectIndex);
            writable.addObject(content.generatorRectRegion, this.objectPathFactory.getPath("gen/rect"));
        } catch (ClassCastException e){
            logger.error("Could not get writable index! This is a development error and should be addressed immediately!", e);
        } catch (FoxCommandException e){
            logger.error("Could not get object path! This is a development error and should be addressed immediately!");
        }
    }

    public PlatformCommandManager getCommandManager() {
        return commandManager.get();
    }

    public ConsoleSource getConsoleSource() {
        return consoleSource;
    }

}
