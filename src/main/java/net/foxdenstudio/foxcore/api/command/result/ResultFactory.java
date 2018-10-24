package net.foxdenstudio.foxcore.api.command.result;

public interface ResultFactory {

    CommandResult counts(int successes, int failures);

    CommandResult success();

    CommandResult empty();

    CommandResult failure();
}
