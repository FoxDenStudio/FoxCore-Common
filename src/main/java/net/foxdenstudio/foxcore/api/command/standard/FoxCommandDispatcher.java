package net.foxdenstudio.foxcore.api.command.standard;

import net.foxdenstudio.foxcore.platform.command.PlatformMutableCommandDispatcher;

import java.util.Optional;

public interface FoxCommandDispatcher extends FoxStandardCommand, PlatformMutableCommandDispatcher {

    Optional<FoxCommandMapping> registerCommand(Object plugin, FoxStandardCommand command, String primaryAlias, String... secondaryAliases);


}
