package net.foxdenstudio.foxsuite.foxcore.api.command.result;

import net.foxdenstudio.foxsuite.foxcore.platform.command.result.PlatformCommandResult;

public interface FoxCommandResult extends PlatformCommandResult {

    int getSuccesses();

    int getFailures();
}
