package net.foxdenstudio.foxsuite.foxcore.content.command;

import net.foxdenstudio.foxsuite.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxsuite.foxcore.api.command.context.CommandContext;
import net.foxdenstudio.foxsuite.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxsuite.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxsuite.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxsuite.foxcore.content.object.StubObject;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxsuite.foxcore.platform.text.format.TextColors;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Collection;

public class CommandList extends FoxStandardCommandBase {

    private final Provider<StubObject> stubObjectProvider;
    private final FoxMainIndex mainIndex;

    private final TextColors textColors;

    private int id = 1;

    @FoxLogger("command.list")
    Logger logger;

    @Inject
    private CommandList(Provider<StubObject> stubObjectProvider, FoxMainIndex mainIndex, TextColors textColors) {
        this.stubObjectProvider = stubObjectProvider;
        this.mainIndex = mainIndex;
        this.textColors = textColors;
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        CommandContext context = this.commandContextManager.getCommandContext(source);

        Collection<StandardPathComponent> allPaths = context.getNamespace(null).getAllObjectPaths();
        String heading = "The following objects exist:";
        StringBuilder builder = new StringBuilder();
        allPaths.stream()
                .sorted()
                .forEach(path -> builder.append('\n').append(path.toString()));
        source.sendMessage(this.tf.of(textColors.YELLOW, heading, textColors.RESET, builder.toString()));

        return this.resultFactory.empty();
    }

}
