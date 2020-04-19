package net.foxdenstudio.foxcore.api.object.link.slot;

import com.google.common.collect.ImmutableMap;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.reference.LinkReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LinkSlotBase implements LinkSlot {

    private final FoxObject container;
    private final boolean dynamic;
    private final boolean embeddable;

    @Nullable
    private final LinkSlot parent;

    @Nullable
    private FoxObject linked;

    @Nonnull
    private final Map<StandardPathComponent, LinkSlot> subSlots;
    private Map<StandardPathComponent, LinkSlot> subSlotsCopy;

    private boolean freeStructure = false;

    protected LinkSlotBase(FoxObject container, @Nullable LinkSlot parent, boolean dynamic, boolean embeddable) {
        this.container = container;
        this.parent = parent;
        this.dynamic = dynamic;
        this.embeddable = embeddable;

        this.subSlots = new HashMap<>();
    }

    @Override
    public StandardPathComponent slotPath() {
        return null;
    }

    @Override
    public LinkSlotSchema getSchema() {
        return null;
    }

    @Override
    public boolean hasLink() {
        return false;
    }

    @Override
    public boolean isDynamic() {
        return false;
    }

    @Override
    public boolean stillValid() {
        return false;
    }

    @Override
    public Optional<LinkReference> getLinkedObject() {
        return Optional.empty();
    }

    @Override
    public Optional<LinkReference> addObject(FoxObject object, @Nullable StandardPathComponent path) {
        if(path != null) return Optional.empty();
        return Optional.empty();
    }

    @Override
    public FoxObject getContainerObject() {
        return this.container;
    }

    @Override
    public Optional<LinkSlot> getParentLinkSlot() {
        return Optional.ofNullable(this.parent);
    }

    @Override
    public Map<StandardPathComponent, LinkSlot> getKnownSubSlots() {
        return this.subSlotsCopy;
    }

    @Override
    public Optional<LinkSlot> getSubSlot(StandardPathComponent path) {
        return Optional.ofNullable(this.subSlotsCopy.get(path));
    }

    @Override
    public boolean freeStructure() {
        return this.freeStructure;
    }

    @Override
    public boolean addSubSlot(LinkSlot slot, StandardPathComponent path) {
        if (!freeStructure || this.subSlots.containsKey(path) || this.subSlots.containsValue(slot)) return false;
        this.subSlots.put(path, slot);
        this.subSlotsCopy = ImmutableMap.copyOf(this.subSlots);
        return true;
    }

    @Override
    public Optional<LinkSlot> removeSubSlot(StandardPathComponent path) {
        if (!freeStructure || !this.subSlots.containsKey(path)) return Optional.empty();
        LinkSlot slot = this.subSlots.remove(path);
        this.subSlotsCopy = ImmutableMap.copyOf(this.subSlots);
        return Optional.ofNullable(slot);
    }

    @Override
    public boolean supportsEmbedded() {
        return this.embeddable;
    }
}
