package net.foxdenstudio.foxcore;

import net.foxdenstudio.foxcore.api.annotation.command.FoxMainDispatcher;
import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.api.command.FoxCommandDispatcher;
import net.foxdenstudio.foxcore.content.command.CommandEcho;
import net.foxdenstudio.foxcore.api.command.FoxCommandManager;
import net.foxdenstudio.foxcore.platform.command.source.ConsoleSource;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FoxCore {

    private final FoxCommandManager commandManager;
    private final FoxCommandDispatcher mainCommandDispatcher;
    private final ConsoleSource consoleSource;

    @FoxLogger("wolf")
    private Logger logger;

    @Inject
    private CommandEcho commandEcho;

    @Inject
    public FoxCore(
            FoxCommandManager commandManager,
            @FoxMainDispatcher FoxCommandDispatcher mainCommandDispatcher,
            ConsoleSource consoleSource) {
        this.commandManager = commandManager;
        this.mainCommandDispatcher = mainCommandDispatcher;
        this.consoleSource = consoleSource;
    }

    public void awoo(){
        logger.info("Awoo!");
    }

    public void registerCommands(){
        this.commandManager.registerCommand(this.mainCommandDispatcher, "fox");
        this.mainCommandDispatcher.registerCommand(commandEcho, "echo");
    }

    public FoxCommandManager getCommandManager() {
        return commandManager;
    }

    public ConsoleSource getConsoleSource() {
        return consoleSource;
    }
}
