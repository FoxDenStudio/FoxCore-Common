package net.foxdenstudio.foxcore;

import net.foxdenstudio.foxcore.api.annotation.command.FoxMainDispatcher;
import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.api.command.standard.FoxCommandDispatcher;
import net.foxdenstudio.foxcore.api.command.standard.FoxCommandManager;
import net.foxdenstudio.foxcore.content.command.CommandEcho;
import net.foxdenstudio.foxcore.content.command.CommandPath;
import net.foxdenstudio.foxcore.platform.command.source.ConsoleSource;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FoxCore {

    private final FoxCommandManager commandManager;
    private final FoxCommandDispatcher mainCommandDispatcher;
    private final ConsoleSource consoleSource;

    @FoxLogger("greeting")
    private Logger logger;

    @Inject
    private CommandEcho commandEcho;

    @Inject
    private CommandPath commandPath;

    @Inject
    public FoxCore(
            FoxCommandManager commandManager,
            @FoxMainDispatcher FoxCommandDispatcher mainCommandDispatcher,
            ConsoleSource consoleSource) {
        this.commandManager = commandManager;
        this.mainCommandDispatcher = mainCommandDispatcher;
        this.consoleSource = consoleSource;
    }

    public void awoo() {
        logger.info("Awoo!");
    }

    public void registerCommands() {
        this.commandManager.registerCommand(this, this.mainCommandDispatcher, "fox");
        this.mainCommandDispatcher.registerCommand(this, commandEcho, "echo");
        this.mainCommandDispatcher.registerCommand(this, commandPath, "path");
    }

    public FoxCommandManager getCommandManager() {
        return commandManager;
    }

    public ConsoleSource getConsoleSource() {
        return consoleSource;
    }

}
