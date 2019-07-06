package net.foxdenstudio.foxcore.api.command.standard;

import java.util.Set;

public interface FoxCommandMapping {

    String getPrimaryAlias();

    Set<String> getAllAliases();

    FoxStandardCommand getCallable();

}
