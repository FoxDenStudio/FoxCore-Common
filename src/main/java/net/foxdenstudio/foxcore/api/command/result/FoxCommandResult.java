package net.foxdenstudio.foxcore.api.command.result;

import net.foxdenstudio.foxcore.platform.command.result.PlatformCommandResult;

public interface FoxCommandResult extends PlatformCommandResult {

    int getSuccesses();

    int getFailures();
}
