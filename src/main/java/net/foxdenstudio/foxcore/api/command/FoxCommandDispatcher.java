package net.foxdenstudio.foxcore.api.command;

public interface FoxCommandDispatcher extends FoxStandardCommand {
    boolean registerCommand(FoxStandardCommand command, String name);
}
