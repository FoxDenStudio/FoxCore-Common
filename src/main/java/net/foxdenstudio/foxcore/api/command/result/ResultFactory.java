package net.foxdenstudio.foxcore.api.command.result;

public interface ResultFactory {

    FoxCommandResult counts(int successes, int failures);

    FoxCommandResult success();

    FoxCommandResult empty();

    FoxCommandResult failure();
}
