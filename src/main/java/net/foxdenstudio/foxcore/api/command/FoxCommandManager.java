package net.foxdenstudio.foxcore.api.command;

public interface FoxCommandManager extends FoxDispatcher {

    boolean registerCommand(FoxStandardCommand command, String name);
}
