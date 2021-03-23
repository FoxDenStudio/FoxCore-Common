package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.FoxPathExt;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxcore.api.path.section.IndexPathSection;
import net.foxdenstudio.foxcore.api.path.section.LinkPathSection;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.path.section.ObjectPathSection;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.text.Text;
import net.foxdenstudio.foxcore.platform.text.TextRepresentable;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Optional;

public class CommandPath extends FoxStandardCommandBase {

    private final FoxPathFactory pathFactory;

    @Inject
    private CommandPath(FoxPathFactory pathFactory) {
        this.pathFactory = pathFactory;
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        FoxPathExt pathExt = (FoxPathExt) this.pathFactory.fromChecked(arguments);
        Optional<IndexPathSection> indexPath = pathExt.getIndexSection();
        Optional<String> indexType = indexPath.map(IndexPathSection::getIndex);
        Optional<StandardPathComponent> namespacePath = indexPath.map(IndexPathSection::getNamespacePath);
        Optional<ObjectPathSection> objectPath = pathExt.getObjectSection();
        Optional<LinkPathSection> linkPaths = pathExt.getLinkSection();

        StringBuilder builder = new StringBuilder();
        builder.append("index:        ").append(indexPath.orElse(null)).append('\n');
        builder.append("  type:       ").append(indexType.orElse(null)).append('\n');
        builder.append("  namespace:  ").append(namespacePath.orElse(null)).append("\n");
        builder.append("object:       ").append(objectPath.orElse(null)).append("\n");
        builder.append("links:");

        int count = 0;
        if (linkPaths.isPresent()) {
            for (StandardPathComponent linkPath : linkPaths.get().getLinkComponents()) {
                String message = count++ + ":            ";
                if (message.length() > 14) message = message.substring(0, 14);
                builder.append("\n").append(message).append(linkPath);
            }
        }

        Text message = this.tf.of("\nfull:         ", pathExt, "\n", builder.toString());

        source.sendMessage(message);

        return this.resultFactory.empty();
    }
}
