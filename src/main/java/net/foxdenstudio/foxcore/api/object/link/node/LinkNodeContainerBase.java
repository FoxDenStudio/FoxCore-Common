package net.foxdenstudio.foxcore.api.object.link.node;

import com.google.common.collect.ImmutableMap;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.reference.types.LinkReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class LinkNodeContainerBase implements LinkNodeContainer {

    @Nonnull
    protected final Map<StandardPathComponent, LinkNode> linkNodes;
    protected final FoxObject containerObject;

    protected transient Map<StandardPathComponent, LinkNode> linkNodesCopy;

    public LinkNodeContainerBase(FoxObject containerObject) {
        this.linkNodes = new HashMap<>();
        this.containerObject = containerObject;
    }

    @Override
    public FoxObject getContainerObject() {
        return this.containerObject;
    }

    @Override
    public Map<StandardPathComponent, LinkNode> getKnownNodes() {
        return this.linkNodesCopy;
    }

    @Override
    public Optional<LinkNode> getNode(StandardPathComponent path, boolean create) {
        return Optional.ofNullable(this.linkNodesCopy.get(path));
    }

    @Override
    public Optional<LinkNode> findNode(StandardPathComponent path, boolean create) {
        Optional<LinkNode> firstNode = this.findFirstNode(path);
        if (firstNode.isPresent()) {
            LinkNode node = firstNode.get();
            StandardPathComponent slotPath = node.nodePath();
            if (slotPath.equals(path)) return Optional.of(node);
            return node.findNode(path.subPath(slotPath.size()));
        }
        return Optional.empty();
    }

    protected Optional<LinkNode> findFirstNode(StandardPathComponent path) {
        for (int i = path.size(); i >= 0; i--) {
            LinkNode node = this.linkNodesCopy.get(path.subPath(0, i));
            if (node != null) return Optional.of(node);
        }
        return Optional.empty();
    }

    @Override
    public boolean addNode(LinkNode slot, StandardPathComponent path) {
        if (this.linkNodes.containsKey(path) || this.linkNodes.containsValue(slot)) return false;
        this.linkNodes.put(path, slot);
        this.linkNodesCopy = ImmutableMap.copyOf(this.linkNodes);
        return true;
    }

    @Override
    public Optional<LinkNode> removeNode(StandardPathComponent path) {
        if (!this.linkNodes.containsKey(path)) return Optional.empty();
        LinkNode node = this.linkNodes.remove(path);
        this.linkNodesCopy = ImmutableMap.copyOf(this.linkNodes);
        return Optional.ofNullable(node);
    }

    @Override
    public Optional<LinkReference> linkObject(FoxObject object, @Nullable StandardPathComponent path) {
        if (path != null) {
            Optional<LinkNode> linkNodeOptional = this.findNode(path, true);
            if (linkNodeOptional.isPresent()) {
                LinkNode node = linkNodeOptional.get();
                return node.linkObject(object);
            }
        }
        return Optional.empty();
    }
}
