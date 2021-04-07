package net.foxdenstudio.foxsuite.foxcore.api.command.context;

import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;


public interface FoxCommandContextManager {

    CommandContext getCommandContext(CommandSource source);

}
