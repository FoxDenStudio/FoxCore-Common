package net.foxdenstudio.foxsuite.foxcore.platform.command;

import java.util.Optional;

public interface PlatformMutableCommandDispatcher extends PlatformCommandDispatcher {

    Optional<? extends PlatformCommandMapping> registerCommand(Object plugin, PlatformCommand command, String primaryAlias, String... secondaryAliases);

}
