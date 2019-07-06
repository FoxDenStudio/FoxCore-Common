package net.foxdenstudio.foxcore.api.command.standard;

import java.util.Optional;

public interface FoxCommandDispatcher extends FoxStandardCommand {

    Optional<FoxCommandMapping> registerCommand(Object plugin, FoxStandardCommand command, String primaryAlias, String... secondaryAliases);


}
