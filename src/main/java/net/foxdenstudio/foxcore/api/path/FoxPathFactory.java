package net.foxdenstudio.foxcore.api.path;

import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.section.FoxPathSection;

public interface FoxPathFactory {

    FoxPath empty();

    FoxPath from(FoxPathSection first, FoxPathSection... next);

    FoxPath from(String input);

    FoxPath fromChecked(String input) throws FoxCommandException;

}
