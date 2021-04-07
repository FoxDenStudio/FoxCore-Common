package net.foxdenstudio.foxsuite.foxcore.impl.command.result;

import net.foxdenstudio.foxsuite.foxcore.api.command.result.FoxCommandResult;

public class FoxCommandResultImpl implements FoxCommandResult {

    private final int successes;
    private final int failures;

    FoxCommandResultImpl(int successes, int failures) {
        this.successes = successes;
        this.failures = failures;
    }

    @Override
    public int getSuccesses() {
        return successes;
    }

    @Override
    public int getFailures() {
        return failures;
    }
}
