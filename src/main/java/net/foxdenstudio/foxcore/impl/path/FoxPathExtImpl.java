package net.foxdenstudio.foxcore.impl.path;

import com.google.common.collect.ImmutableList;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.FoxPathExt;
import net.foxdenstudio.foxcore.api.path.component.FoxPathComponent;
import net.foxdenstudio.foxcore.api.path.component.IndexPathComponent;
import net.foxdenstudio.foxcore.api.path.component.LinkPathComponent;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.path.resolve.ResolveConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FoxPathExtImpl extends FoxPathImpl implements FoxPathExt {

    @Nullable
    private IndexPathComponent indexPathComponent = null;
    @Nullable
    private StandardPathComponent objectPathComponent = null;
    @Nullable
    private LinkPathComponent linkPathComponent = null;
    @Nonnull
    private List<FoxPathComponent> extras;

    @Nonnull
    private Mode mode;
    private int parentOffset;


    public FoxPathExtImpl(@Nonnull List<FoxPathComponent> components, @Nonnull Mode mode, int parentOffset) {
        super(components);
        this.mode = mode;
        this.parentOffset = parentOffset;
        ImmutableList.Builder<FoxPathComponent> builder = ImmutableList.builder();
        for (FoxPathComponent component : components) {
            if (component != null) {
                if (this.indexPathComponent == null && component instanceof IndexPathComponent) {
                    this.indexPathComponent = (IndexPathComponent) component;
                } else if (this.objectPathComponent == null && component instanceof StandardPathComponent) {
                    this.objectPathComponent = ((StandardPathComponent) component);
                } else if (this.linkPathComponent == null && component instanceof LinkPathComponent) {
                    this.linkPathComponent = ((LinkPathComponent) component);
                } else {
                    builder.add(component);
                }
            }
        }
        this.extras = builder.build();
    }

    @Override
    public FoxPathExtImpl resolve(FoxPath path) {
        return resolve(path, new ResolveConfig());
    }

    @Override
    public FoxPathExtImpl resolve(FoxPath path, ResolveConfig config) {
        if (path.isEmpty()) return this;
        Mode mode = Mode.DEFAULT;
        int otherOffset = 0;
        IndexPathComponent otherIndex = null;
        StandardPathComponent otherObject = null;
        LinkPathComponent otherLink = null;
        if (path instanceof FoxPathExt) {
            FoxPathExt pathExt = ((FoxPathExt) path);
            mode = pathExt.getMode();
            otherOffset = pathExt.getParentOffset();
            otherIndex = pathExt.getIndexComponent().orElse(null);
            otherObject = pathExt.getObjectComponent().orElse(null);
            otherLink = pathExt.getLinkComponent().orElse(null);
        } else {
            // TODO add handling for non-EXT paths
            // while not strictly necessary, it makes me feel better about the future.
        }

        if (otherIndex != null && otherObject != null && mode == Mode.LINK) mode = Mode.DEFAULT;

        if (mode == Mode.DEFAULT) mode = config.get(FoxPathExt.DEFAULT_ABSOLUTE) ? Mode.ABSOLUTE : Mode.RELATIVE;

        LinkResolve linkResolve = config.get(FoxPathExt.LINK_RESOLVE);

        IndexPathComponent newIndex = this.indexPathComponent;
        StandardPathComponent newObject = this.objectPathComponent;
        LinkPathComponent newLink = this.linkPathComponent;
        int newOffset = this.parentOffset;
        Mode newMode = this.mode;

        if (mode == Mode.ABSOLUTE) newMode = Mode.ABSOLUTE;

        if (otherIndex != null) newIndex = otherIndex;
        if (otherObject != null) {
            if (linkResolve != LinkResolve.APPEND || this.linkPathComponent == null) {
                if (newObject == null || mode == Mode.ABSOLUTE) {
                    newObject = otherObject;
                    if (mode == Mode.ABSOLUTE) {
                        newOffset = otherOffset;
                    } else {
                        newOffset += otherOffset;
                    }
                } else {
                    List<String> parts = new ArrayList<>(newObject.getElements());
                    for (int i = 0; i < otherOffset; i++) {
                        if (parts.isEmpty()) {
                            newOffset++;
                        } else {
                            parts.remove(parts.size() - 1);
                        }
                    }
                    parts.addAll(otherObject.getElements());
                    newObject = StandardPathComponent.from(parts);
                }
            }
            switch (linkResolve) {
                case REPLACE:
                    if (otherLink != null)
                        newLink = otherLink;
                    break;
                case APPEND:
                    // TODO add append logic
                    break;
                case SPLICE:
                    List<StandardPathComponent> parts = new ArrayList<>();
                    if (newLink != null) parts.addAll(newLink.getLinkComponents());
                    if (otherLink != null) parts.addAll(otherLink.getLinkComponents());
                    newLink = LinkPathComponent.of(parts);
                    break;
            }
        } else {
            newOffset += otherOffset;
        }

        FoxPathExtImpl.Builder newPathBuilder = new Builder();

        newPathBuilder
                .indexComponent(newIndex)
                .objectComponent(newObject)
                .linkComponent(newLink)
                .parentOffset(newOffset)
                .mode(newMode);


        return newPathBuilder.build();
    }

    public FoxPathExtImpl(@Nonnull List<FoxPathComponent> components) {
        this(components, Mode.DEFAULT, 0);
    }

    @Override
    public Optional<IndexPathComponent> getIndexComponent() {
        return Optional.ofNullable(this.indexPathComponent);
    }

    @Override
    public Optional<StandardPathComponent> getObjectComponent() {
        return Optional.ofNullable(this.objectPathComponent);
    }

    @Override
    public Optional<LinkPathComponent> getLinkComponent() {
        return Optional.ofNullable(this.linkPathComponent);
    }

    public List<FoxPathComponent> getExtras() {
        return this.extras;
    }

    @Override
    public Mode getMode() {
        return this.mode;
    }

    @Override
    public int getParentOffset() {
        return this.parentOffset;
    }

    public static class Builder implements FoxPathExt.Builder {

        @Nullable
        IndexPathComponent indexComponent;
        @Nullable
        StandardPathComponent objectComponent;
        @Nullable
        LinkPathComponent linkComponent;
        @Nonnull
        Mode mode = Mode.DEFAULT;
        int parentOffset = 0;

        public Builder() {
        }

        @Override
        @Nonnull
        public FoxPathExt.Builder indexComponent(@Nullable IndexPathComponent component) {
            this.indexComponent = component;
            return this;
        }

        @Override
        @Nonnull
        public FoxPathExt.Builder objectComponent(@Nullable StandardPathComponent component) {
            this.objectComponent = component;
            return this;
        }

        @Override
        @Nonnull
        public FoxPathExt.Builder linkComponent(@Nullable LinkPathComponent component) {
            this.linkComponent = component;
            return this;
        }

        @Override
        @Nonnull
        public FoxPathExt.Builder mode(@Nonnull Mode mode) {
            this.mode = mode;
            return this;
        }

        @Override
        @Nonnull
        public FoxPathExt.Builder parentOffset(int offset) {
            this.parentOffset = offset;
            return this;
        }

        @Override
        @Nonnull
        public FoxPathExtImpl build() {
            ImmutableList.Builder<FoxPathComponent> builder = ImmutableList.builder();
            if (indexComponent != null) builder.add(indexComponent);
            if (objectComponent != null) builder.add(objectComponent);
            if (linkComponent != null) builder.add(linkComponent);
            return new FoxPathExtImpl(builder.build(), mode, parentOffset);
        }
    }
}
