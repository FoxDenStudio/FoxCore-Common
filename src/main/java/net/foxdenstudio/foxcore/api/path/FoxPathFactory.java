package net.foxdenstudio.foxcore.api.path;

import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.component.FoxPathComponent;

public interface FoxPathFactory {

    FoxPath from(FoxPathComponent first, FoxPathComponent... next);

    FoxPath from(String input);

    FoxPath fromChecked(String input) throws FoxCommandException;

}
