package net.foxdenstudio.foxcore.impl.command.result;

import net.foxdenstudio.foxcore.api.command.result.CommandResult;

public class CommandResultImpl implements CommandResult {

    private final int success;
    private final int failure;

    CommandResultImpl(int success, int failure) {
        this.success = success;
        this.failure = failure;
    }
}
