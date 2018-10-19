package net.foxdenstudio.foxcore.common;

import net.foxdenstudio.foxcore.common.annotation.guice.InjectFoxLogger;
import net.foxdenstudio.foxcore.common.command.CommandEcho;
import net.foxdenstudio.foxcore.common.command.FoxCommandManager;
import net.foxdenstudio.foxcore.common.platform.command.source.ConsoleSource;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FoxCore {

    private final FoxCommandManager commandManager;
    private final ConsoleSource consoleSource;

    @InjectFoxLogger("wolf")
    Logger logger;

    @Inject
    CommandEcho commandEcho;

    @Inject
    public FoxCore(FoxCommandManager commandManager, ConsoleSource consoleSource) {
        this.commandManager = commandManager;
        this.consoleSource = consoleSource;
    }

    public void awoo(){
        logger.info("Awoo!");
    }

    public void registerCommands(){
        this.commandManager.registerCommand(commandEcho, "echo");
    }

    public FoxCommandManager getCommandManager() {
        return commandManager;
    }

    public ConsoleSource getConsoleSource() {
        return consoleSource;
    }
}
