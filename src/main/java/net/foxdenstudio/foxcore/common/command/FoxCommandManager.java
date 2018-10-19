package net.foxdenstudio.foxcore.common.command;

public interface FoxCommandManager extends FoxDispatcher {

    boolean registerCommand(FoxCommand command, String name);
}
