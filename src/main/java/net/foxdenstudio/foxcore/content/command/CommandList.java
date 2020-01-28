package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.api.command.context.CommandContext;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxcore.api.object.index.types.MemoryIndex;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.content.object.StubObject;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.text.format.TextColors;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Collection;
import java.util.Comparator;

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
                .sorted(Comparator.comparing(StandardPathComponent::toString))
                .forEach(path -> builder.append('\n').append(path.toString()));
        source.sendMessage(this.tf.of(textColors.YELLOW, heading, textColors.RESET, builder.toString()));

        return this.resultFactory.empty();
    }

}
