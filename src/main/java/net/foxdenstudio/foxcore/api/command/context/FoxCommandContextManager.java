package net.foxdenstudio.foxcore.api.command.context;

import net.foxdenstudio.foxcore.platform.command.source.CommandSource;


public interface FoxCommandContextManager {

    CommandContext getCommandContext(CommandSource source);

}
