package net.foxdenstudio.foxcore.api.object.link.node;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.link.LinkContainer;
import net.foxdenstudio.foxcore.api.object.reference.types.LinkReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nullable;
import java.util.Optional;

public class FoxBasicLinkNodeContainer extends LinkNodeContainerBase implements LinkContainer {

    protected LinkNode defaultLinkNode;

    public FoxBasicLinkNodeContainer(FoxObject containerObject) {
        super(containerObject);
    }

    @Override
    public Optional<LinkNode> getDefaultLinkNode() {
        return Optional.empty();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void invalidate() {
    }

    @Override
    public Optional<LinkReference> linkObject(FoxObject object, @Nullable StandardPathComponent path) {
        return Optional.empty();
    }

    @Override
    public boolean addNode(LinkNode node, StandardPathComponent path) {
        return this.addNode(node, path, false);
    }

    @Override
    public boolean addNode(LinkNode node, StandardPathComponent path, boolean setAsDefault, boolean overrideExistingDefault) {
        boolean b = super.addNode(node, path);
        if (b && setAsDefault && (overrideExistingDefault || this.defaultLinkNode == null)) {
            this.defaultLinkNode = node;
        }
        return b;
    }
}
