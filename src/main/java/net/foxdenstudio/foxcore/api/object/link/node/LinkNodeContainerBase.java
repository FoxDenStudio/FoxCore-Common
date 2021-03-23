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
        if (this.linkNodesCopy == null) this.linkNodesCopy = ImmutableMap.of();
        return this.linkNodesCopy;
    }

    @Override
    public Optional<LinkNode> getNode(@Nonnull StandardPathComponent path, boolean create) {
        if (path.isEmpty()) return Optional.empty();
        return Optional.ofNullable(this.linkNodesCopy).map(nodes -> nodes.get(path));
    }

    @Override
    public Optional<LinkNode> findNode(@Nonnull StandardPathComponent path, boolean create) {
        if (path.isEmpty()) return Optional.empty();
        Optional<LinkNode> firstNode = this.findFirst(path, create);
        if (firstNode.isPresent()) {
            LinkNode node = firstNode.get();
            StandardPathComponent slotLocalPath = node.localNodePath();
            if (slotLocalPath.equals(path)) return Optional.of(node);
            return node.findNode(path.subPath(slotLocalPath.length()), create);
        }
        return Optional.empty();
    }

    public Optional<LinkNode> findFirst(@Nonnull StandardPathComponent path, boolean create) {
        if (path.isEmpty()) return Optional.empty();
        // This is the rule of specific priority, where longer paths take priority over shorter ones
        // TODO figure out whether this should be inverted or otherwise prevent prefix collisions.
        for (int i = path.length(); i >= 0; i--) {
            LinkNode node = this.linkNodesCopy.get(path.subPath(0, i));
            if (node != null) return Optional.of(node);
        }
        return Optional.empty();
    }

    @Override
    public boolean addNode(@Nonnull LinkNode node, @Nullable StandardPathComponent path) {
        // TODO add some abstraction to handle default routing when no path is supplied.
        if (path == null || path.isEmpty()) return false;
        if (this.linkNodes.containsKey(path) || this.linkNodes.containsValue(node)) return false;
        this.linkNodes.put(path, node);
        this.linkNodesCopy = ImmutableMap.copyOf(this.linkNodes);
        return true;
    }

    @Override
    public Optional<LinkNode> removeNode(@Nonnull StandardPathComponent path) {
        if (path.isEmpty()) return Optional.empty();
        if (!this.linkNodes.containsKey(path)) return Optional.empty();
        LinkNode node = this.linkNodes.remove(path);
        this.linkNodesCopy = ImmutableMap.copyOf(this.linkNodes);
        return Optional.ofNullable(node);
    }

    @Override
    public Optional<LinkReference> linkObject(@Nonnull FoxObject object, @Nullable StandardPathComponent path) {
        if (path != null && !path.isEmpty()) {
            Optional<LinkNode> linkNodeOptional = this.findFirst(path, true);
            if (linkNodeOptional.isPresent()) {
                LinkNode node = linkNodeOptional.get();
                return node.linkObject(object, path.subPath(node.localNodePath().length()));
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean acceptsObject(@Nonnull FoxObject object, @Nullable StandardPathComponent path) {
        if (path != null && !path.isEmpty()) {
            Optional<LinkNode> linkNodeOptional = this.findNode(path, true);
            if (linkNodeOptional.isPresent()) {
                LinkNode node = linkNodeOptional.get();
                return node.acceptsObject(object);
            }
        }
        return false;
    }
}
