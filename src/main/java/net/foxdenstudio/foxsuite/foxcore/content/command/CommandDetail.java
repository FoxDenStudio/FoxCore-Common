package net.foxdenstudio.foxsuite.foxcore.content.command;

import net.foxdenstudio.foxsuite.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxsuite.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxsuite.foxcore.api.command.context.CommandContext;
import net.foxdenstudio.foxsuite.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxsuite.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxsuite.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxsuite.foxcore.api.object.FoxDetailableObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.LinkContainer;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.node.LinkNode;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.node.LinkSlot;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.FoxObjectReference;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.IndexReference;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxsuite.foxcore.platform.text.Text;
import net.foxdenstudio.foxsuite.foxcore.platform.text.TextRepresentable;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.*;

public class CommandDetail extends FoxStandardCommandBase {

    private final FoxPathFactory pathFactory;

    @Inject
    private CommandDetail(FoxPathFactory pathFactory) {
        this.pathFactory = pathFactory;
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        arguments = arguments.trim();
        if (arguments.isEmpty()) {
            source.sendMessage(tf.of("Syntax: <name/path>"));
            return resultFactory.empty();
        }
        String[] args = arguments.split(" +", 2);
        String objectPathStr = args[0];
        FoxPath objectPath = this.pathFactory.fromChecked(objectPathStr);
        CommandContext context = this.commandContextManager.getCommandContext(source);

        IndexReference reference = context.getObjectFromIndex(objectPath);
        FoxObject object = reference.getObject().orElse(null);
        if (object == null) throw new FoxCommandException("Object missing for reference!");

        FoxArchetype archetype = object.getArchetype();

        Text.Builder builder = tf.builder();
        builder.append(tf.of(
                "\n--------------------------------------",
                tc.GOLD, "\nInfo about object: ",
                tc.AQUA, objectPathStr.toLowerCase(),
                tc.GREEN, "\nArchetype: ",
                tc.RESET, archetype.getName() + " (" + archetype.getType() + ")",
                tc.AQUA, "\nAttributes:"
        ));
        for (FoxAttribute<?> attribute : object.getAttributes()) {
            if (attribute instanceof TextRepresentable) {
                builder.append(tf.of("\n  ", attribute));
            } else {
                builder.append(tf.of("\n  ", tc.GREEN, attribute.getDisplayName()));
            }
            builder.append(tf.of(": ", object.getAttrValueWeak(attribute).orElse(null)));
        }

        builder.append(tf.of(tc.GOLD, "\n--- Links ---\n"));

        LinkContainer lc = object.getLinkContainer();
        List<Link> list = new LinkedList<>();
        lc.getKnownNodes().values().forEach(node -> collect(node, list));

        if (list.isEmpty()) {
            builder.append(tf.of(tc.GRAY, "No links"));
        } else {
            for (Link link : list) {
            builder.append(tf.of(link.slotPath.toString(), tc.YELLOW, " -> ", tc.RESET, link.target.getPrimeReference()
                    .flatMap(IndexReference::getPrimePath)
                    .map(FoxPath::toString)
                    .orElse("<MISSING!>")));
            }
        }


        if (object instanceof FoxDetailableObject) {
            String detArgs = args.length > 1 ? args[1] : "";
            builder.append(tf.of(tc.GOLD, "\n--- Extra Details ---\n"));
            builder.append(((FoxDetailableObject) object).details(source, detArgs));
        }
        source.sendMessage(builder.build());

        return this.resultFactory.success();
    }

    private void collect(LinkNode node, Collection<Link> collection) {
        if (node instanceof LinkSlot) {
            ((LinkSlot) node).getLinkedObject().ifPresent(obj -> collection.add(new Link(node.nodePath(), obj)));
        }
        for (LinkNode child : node.getKnownNodes().values()) {
            collect(child, collection);
        }
    }

    private class Link {
        StandardPathComponent slotPath;
        FoxObjectReference target;

        public Link(StandardPathComponent slotPath, FoxObjectReference target) {
            this.slotPath = slotPath;
            this.target = target;
        }
    }

}
