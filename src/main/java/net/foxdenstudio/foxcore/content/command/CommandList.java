package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxcore.api.object.index.types.MemoryIndex;
import net.foxdenstudio.foxcore.api.path.components.FoxObjectPath;
import net.foxdenstudio.foxcore.api.path.factory.FoxObjectPathFactory;
import net.foxdenstudio.foxcore.content.object.StubObject;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.text.format.TextColors;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Collection;
import java.util.Comparator;

public class CommandList extends FoxStandardCommandBase {

    private final Provider<StubObject> stubObjectProvider;
    private final FoxObjectPathFactory objectPathFactory;
    private final FoxMainIndex mainIndex;

    private final TextColors textColors;

    private int id = 1;

    @FoxLogger("command.list")
    Logger logger;

    @Inject
    private CommandList(Provider<StubObject> stubObjectProvider, FoxObjectPathFactory objectPathFactory, FoxMainIndex mainIndex, TextColors textColors){
        this.stubObjectProvider = stubObjectProvider;
        this.objectPathFactory = objectPathFactory;
        this.mainIndex = mainIndex;
        this.textColors = textColors;
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        //StubObject object = this.stubObjectProvider.get();
        //String objectName = "awoo-stub-" + (id++);
        //FoxObjectPath newPath = this.objectPathFactory.getPath(objectName);
        FoxObjectIndex objectIndex = mainIndex.getDefaultObjectIndex();
        if(objectIndex instanceof MemoryIndex){
            MemoryIndex memoryIndex = (MemoryIndex) objectIndex;
            //memoryIndex.addObject(object, newPath);
        } else {
            source.sendMessage(this.textFactory.getText("wtf!?"));
        }

        Collection<FoxObjectPath> allPaths = objectIndex.getAllObjectPaths();
        String heading = "The following objects exist:";
        StringBuilder builder = new StringBuilder();
        allPaths.stream()
                .sorted(Comparator.comparing(FoxObjectPath::toString))
                .forEach(path -> builder.append('\n').append(path.toString()));
        source.sendMessage(this.textFactory.getText(textColors.YELLOW, heading, textColors.RESET, builder.toString()));



        /*source.sendMessage(this.textFactory.getText(
                textColors.BLACK, "BLACK\n",
                textColors.DARK_BLUE, "DARK_BLUE\n",
                textColors.DARK_GREEN, "DARK_GREEN\n",
                textColors.DARK_AQUA, "DARK_AQUA\n",
                textColors.DARK_RED, "DARK_RED\n",
                textColors.DARK_PURPLE, "DARK_PURPLE\n",
                textColors.GOLD, "GOLD\n",
                textColors.GRAY, "GRAY\n",
                textColors.DARK_GRAY, "DARK_GRAY\n",
                textColors.BLUE, "BLUE\n",
                textColors.GREEN, "GREEN\n",
                textColors.AQUA, "AQUA\n",
                textColors.RED, "RED\n",
                textColors.LIGHT_PURPLE, "LIGHT_PURPLE\n",
                textColors.YELLOW, "YELLOW\n",
                textColors.WHITE, "WHITE\n"
                ));*/

        //source.sendMessage(this.textFactory.getText("Here's a list: yip, bark, awoo!"));

        return this.resultFactory.empty();
    }

}
