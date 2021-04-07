package net.foxdenstudio.foxsuite.foxcore.api.object;

import net.foxdenstudio.foxsuite.foxcore.api.command.FoxCommand;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxsuite.foxcore.platform.text.Text;

public interface FoxDetailableObject extends FoxObject, FoxCommand {

    Text details(CommandSource source, String arguments);

}
