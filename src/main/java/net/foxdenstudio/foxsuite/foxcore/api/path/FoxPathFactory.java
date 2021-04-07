package net.foxdenstudio.foxsuite.foxcore.api.path;

import com.google.gson.TypeAdapterFactory;
import net.foxdenstudio.foxsuite.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxsuite.foxcore.api.path.section.FoxPathSection;

import java.util.List;

public interface FoxPathFactory {

    FoxPath empty();

    FoxPath root();

    FoxPath from(FoxPathSection first, FoxPathSection... next);

    FoxPath from(List<FoxPathSection> sections);

    FoxPath from(String input);

    FoxPath fromChecked(String input) throws FoxCommandException;

    TypeAdapterFactory getTypeAdapterFactory();

}
