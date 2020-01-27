package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.FoxPathExt;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxcore.api.path.component.IndexPathComponent;
import net.foxdenstudio.foxcore.api.path.component.LinkPathComponent;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.text.Text;

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
        Optional<IndexPathComponent> indexPath = pathExt.getIndexComponent();
        Optional<String> indexType = indexPath.map(IndexPathComponent::getIndex);
        Optional<StandardPathComponent> namespacePath = indexPath.map(IndexPathComponent::getNamespacePath);
        Optional<StandardPathComponent> objectPath = pathExt.getObjectComponent();
        Optional<LinkPathComponent> linkPaths = pathExt.getLinkComponent();

        StringBuilder builder = new StringBuilder();
        builder.append("full:         ").append(pathExt).append('\n');
        builder.append("index:        ").append(indexPath.orElse(null)).append('\n');
        builder.append("  type:       ").append(indexType.orElse(null)).append('\n');
        builder.append("  namespace: ").append(namespacePath.orElse(null)).append("\n");
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

        Text message = this.tf.of(builder.toString());

        source.sendMessage(message);

        return this.resultFactory.empty();
    }
}
