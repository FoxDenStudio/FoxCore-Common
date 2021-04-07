package net.foxdenstudio.foxsuite.foxcore.platform.command;

import java.util.Set;

public interface PlatformCommandMapping {
    String getPrimaryAlias();

    Set<String> getAllAliases();

    PlatformCommand getCallable();
}
