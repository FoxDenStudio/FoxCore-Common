package net.foxdenstudio.foxcore.impl.command.result;

import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;

public class FoxCommandResultImpl implements FoxCommandResult {

    private final int success;
    private final int failure;

    FoxCommandResultImpl(int success, int failure) {
        this.success = success;
        this.failure = failure;
    }
}
