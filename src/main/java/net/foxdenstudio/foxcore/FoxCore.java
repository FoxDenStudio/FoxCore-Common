package net.foxdenstudio.foxcore;

import net.foxdenstudio.foxcore.api.annotation.FoxCorePluginInstance;
import net.foxdenstudio.foxcore.api.annotation.command.FoxMainDispatcher;
import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.api.command.standard.FoxCommandDispatcher;
import net.foxdenstudio.foxcore.content.command.CommandEcho;
import net.foxdenstudio.foxcore.content.command.CommandList;
import net.foxdenstudio.foxcore.content.command.CommandPath;
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

    @FoxLogger("greeting")
    private Logger logger;

    @Inject
    private CommandEcho commandEcho;

    @Inject
    private CommandPath commandPath;

    @Inject
    private CommandList commandList;

    @com.google.inject.Inject(optional = true)
    @FoxCorePluginInstance
    Object foxCorePlugin = this;

    @Inject
    public FoxCore(
            Provider<PlatformCommandManager> commandManager,
            @FoxMainDispatcher FoxCommandDispatcher mainCommandDispatcher,
            ConsoleSource consoleSource) {
        this.commandManager = commandManager;
        this.mainCommandDispatcher = mainCommandDispatcher;
        this.consoleSource = consoleSource;
    }

    public void awoo() {
        logger.info("Awoo!");
    }

    public void configureCommands() {
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, commandEcho, "echo");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, commandPath, "path");
        this.mainCommandDispatcher.registerCommand(this.foxCorePlugin, commandList, "list");
    }

    public void registerCommands() {
        this.commandManager.get().registerCommand(this.foxCorePlugin, this.mainCommandDispatcher, "fox");
    }

    public PlatformCommandManager getCommandManager() {
        return commandManager.get();
    }

    public ConsoleSource getConsoleSource() {
        return consoleSource;
    }

}
