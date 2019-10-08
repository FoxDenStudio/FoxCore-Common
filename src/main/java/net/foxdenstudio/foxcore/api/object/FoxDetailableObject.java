package net.foxdenstudio.foxcore.api.object;

import net.foxdenstudio.foxcore.api.command.FoxCommand;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.text.Text;

public interface FoxDetailableObject extends FoxObject, FoxCommand {

    Text details(CommandSource source, String arguments);

}
