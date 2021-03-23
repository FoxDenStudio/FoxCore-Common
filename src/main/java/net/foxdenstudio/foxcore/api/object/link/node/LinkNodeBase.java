package net.foxdenstudio.foxcore.api.object.link.node;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.link.schema.LinkSchema;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public abstract class LinkNodeBase extends LinkNodeContainerBase implements LinkNode {

    protected final LinkSchema schema;
    protected final LinkNodeContainer parent;
    protected final StandardPathComponent nodePath;
    protected final StandardPathComponent localNodePath;
    protected final boolean dynamic;
    protected final boolean embeddable;
    protected boolean freeStructure = false;
    protected boolean valid = true;

    public LinkNodeBase(FoxObject containerObject, LinkSchema schema, LinkNodeContainer parent, StandardPathComponent nodePath, int localPathLength, boolean dynamic, boolean embeddable) {
        super(containerObject);
        this.schema = schema;
        this.parent = parent;
        this.nodePath = nodePath;
        this.localNodePath = nodePath.subPath(nodePath.length() - localPathLength);
        this.dynamic = dynamic;
        this.embeddable = embeddable;
    }

    @Override
    public StandardPathComponent nodePath() {
        return this.nodePath;
    }

    @Override
    public StandardPathComponent localNodePath() {
        return this.localNodePath;
    }

    @Override
    public LinkSchema getSchema() {
        return this.schema;
    }

    @Override
    public boolean isDynamic() {
        return this.dynamic;
    }

    @Override
    public boolean isTransient() {
        return this.dynamic && this.linkNodes.isEmpty();
    }

    @Override
    public boolean isValid() {
        if (!this.parent.isValid()) this.valid = false;
        return this.valid;
    }

    @Override
    public void invalidate() {
        if (!this.parent.isValid()) {
            this.valid = false;
            return;
        }
        Optional<LinkNode> node = this.parent.getNode(this.localNodePath);
        if (node.isPresent()) {
            if (node.get().equals(this)) return;
        }
        this.valid = false;
    }

    @Override
    public LinkNodeContainer getParentContainer() {
        return this.parent;
    }

    @Override
    public boolean structured() {
        return this.freeStructure;
    }

    @Override
    public boolean supportsEmbedding() {
        if (this.parent instanceof LinkNode && !((LinkNode) this.parent).supportsEmbedding()) return false;
        return this.embeddable;
    }

    @Override
    public boolean addNode(@Nonnull LinkNode node, @Nullable StandardPathComponent path) {
        if (!freeStructure) return false;
        return super.addNode(node, path);
    }

    protected boolean addNodeInternal(@Nonnull LinkNode slot, @Nullable StandardPathComponent path){
        return super.addNode(slot, path);
    }

    @Override
    public Optional<LinkNode> removeNode(@Nonnull StandardPathComponent path) {
        if (!freeStructure) return Optional.empty();
        return super.removeNode(path);
    }
}
