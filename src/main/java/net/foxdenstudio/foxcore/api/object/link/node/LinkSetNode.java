package net.foxdenstudio.foxcore.api.object.link.node;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.link.schema.LinkSchema;
import net.foxdenstudio.foxcore.api.object.reference.types.LinkReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class LinkSetNode extends LinkNodeBase {

    public LinkSetNode(FoxObject containerObject, LinkSchema schema, LinkNodeContainer parent, StandardPathComponent nodePath, int localPathLength, boolean dynamic, boolean embeddable) {
        super(containerObject, schema, parent, nodePath, localPathLength, dynamic, embeddable);
    }

    @Override
    public Optional<LinkReference> linkObject(@Nonnull FoxObject object, @Nullable StandardPathComponent path) {
        if (path != null && !path.isEmpty()) {
            Optional<LinkNode> firstOpt = this.findFirst(path, false);
            if (firstOpt.isPresent()) {
                LinkNode first = firstOpt.get();
                if (!(first instanceof Leaf)) {
                    return first.linkObject(object, path.subPath(first.localNodePath().length()));
                } else if (((Leaf) first).hasLink()) return Optional.empty();
            }
        } else {
            LinkNode node;
            while (true) {
                int i = 1;
                path = StandardPathComponent.of("o" + i);
                node = this.linkNodesCopy == null ? null : this.linkNodesCopy.get(path);
                if (node == null || node instanceof Leaf && !((Leaf) node).hasLink()) {
                    break;
                }
            }
            node = this.getNode(path, true).orElse(null);
            if (node != null) return node.linkObject(object);
        }

        return Optional.empty();
    }

    @Override
    public Optional<LinkNode> getNode(@Nonnull StandardPathComponent path, boolean create) {
        if (path.isEmpty()) return Optional.empty();
        Optional<LinkNode> node = super.getNode(path, create);
        if (!node.isPresent() && create) {
            Leaf leaf = new Leaf(this.nodePath.concat(path), path.length());
            this.addNodeInternal(leaf, path);
            node = Optional.of(leaf);
        }
        return node;
    }

    @Override
    public Optional<LinkNode> findFirst(@Nonnull StandardPathComponent path, boolean create) {
        if (path.isEmpty()) return Optional.empty();
        Optional<LinkNode> node = super.findFirst(path, create);
        if (!node.isPresent() && create) {
            Leaf leaf = new Leaf(this.nodePath.concat(path), path.length());
            this.addNodeInternal(leaf, path);
            node = Optional.of(leaf);
        }
        return node;
    }

    public class Leaf extends LinkSlotBase {

        protected Leaf(StandardPathComponent nodePath, int localPathLength) {
            super(LinkSetNode.this.containerObject, LinkSetNode.this.schema, LinkSetNode.this, nodePath, localPathLength, true, LinkSetNode.this.embeddable);
        }
    }
}
