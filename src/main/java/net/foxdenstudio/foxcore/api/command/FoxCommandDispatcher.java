package net.foxdenstudio.foxcore.api.command;

public interface FoxCommandDispatcher extends FoxStandardCommand {

    boolean registerCommand(Object plugin, FoxStandardCommand command, String name);

}
