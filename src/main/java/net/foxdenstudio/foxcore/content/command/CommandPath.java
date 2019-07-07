package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.components.*;
import net.foxdenstudio.foxcore.api.path.factory.FoxFullPathFactory;
import net.foxdenstudio.foxcore.platform.command.CommandSource;
import net.foxdenstudio.foxcore.platform.text.Text;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;

public class CommandPath extends FoxStandardCommandBase {

    private final FoxFullPathFactory fullPathFactory;

    @Inject
    private CommandPath(FoxFullPathFactory fullPathFactory) {
        this.fullPathFactory = fullPathFactory;
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        FoxFullPath fullPath = this.fullPathFactory.getPath(arguments);
        FoxIndexPath indexPath = fullPath.getIndexPath();
        String indexType = indexPath.getIndexType();
        FoxNamespacePath namespacePath = indexPath.getNamespacePath();
        FoxObjectPath objectPath = fullPath.getObjectPath();
        List<FoxLinkPath> linkPaths = fullPath.getLinkPaths();

        StringBuilder builder = new StringBuilder();
        builder.append("full:         ").append(fullPath).append('\n');
        builder.append("index:        ").append(indexPath).append('\n');
        builder.append("  type:       ").append(indexType).append('\n');
        builder.append("  namesapace: ").append(namespacePath).append("\n");
        builder.append("object:       ").append(objectPath).append("\n");
        builder.append("links:");

        int count = 0;
        for(FoxLinkPath linkPath : linkPaths){
            String message = count++ + ":            ";
            if(message.length() > 14) message = message.substring(0, 14);
            builder.append("\n").append(message).append(linkPath);
        }

        Text message = this.textFactory.getText(builder.toString());

        source.sendMessage(message);

        return this.resultFactory.empty();
    }
}
