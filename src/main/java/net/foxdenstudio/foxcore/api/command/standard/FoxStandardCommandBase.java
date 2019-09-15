package net.foxdenstudio.foxcore.api.command.standard;

import com.google.common.collect.ImmutableList;
import net.foxdenstudio.foxcore.api.command.FoxCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;

public abstract class FoxStandardCommandBase extends FoxCommandBase implements FoxStandardCommand {

    protected FoxStandardCommandBase() {
    }

    @Override
    public List<String> getSuggestions(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        return ImmutableList.of();
    }
}
