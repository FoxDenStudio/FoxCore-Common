package net.foxdenstudio.foxcore.platform.command;

import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommand;

import java.util.Set;

public interface PlatformCommandMapping {
    String getPrimaryAlias();

    Set<String> getAllAliases();

    PlatformCommand getCallable();
}
