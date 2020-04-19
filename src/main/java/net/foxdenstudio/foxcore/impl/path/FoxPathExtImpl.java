package net.foxdenstudio.foxcore.impl.path;

import com.google.common.collect.ImmutableList;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.FoxPathExt;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.path.resolve.ResolveConfig;
import net.foxdenstudio.foxcore.api.path.section.FoxPathSection;
import net.foxdenstudio.foxcore.api.path.section.IndexPathSection;
import net.foxdenstudio.foxcore.api.path.section.LinkPathSection;
import net.foxdenstudio.foxcore.api.path.section.ObjectPathSection;
import net.foxdenstudio.foxcore.api.text.FoxTextRepresentable;
import net.foxdenstudio.foxcore.platform.fox.text.TextFactory;
import net.foxdenstudio.foxcore.platform.text.Text;
import net.foxdenstudio.foxcore.platform.text.TextRepresentable;
import net.foxdenstudio.foxcore.platform.text.format.TextColors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class FoxPathExtImpl extends FoxPathImpl implements FoxPathExt, FoxTextRepresentable {

    private final TextFactory textFactory;
    private final TextColors textColors;
    private final Provider<Builder> builderProvider;

    @Nullable
    private IndexPathSection indexPathSection = null;
    @Nullable
    private ObjectPathSection objectPathSection = null;
    @Nullable
    private LinkPathSection linkPathSection = null;
    @Nonnull
    private List<FoxPathSection> extras;

    @Nonnull
    private Mode mode;
    private int parentOffset;

    public FoxPathExtImpl(TextFactory textFactory, TextColors textColors, Provider<Builder> builderProvider,
                          @Nonnull List<FoxPathSection> components, @Nonnull Mode mode, int parentOffset) {
        super(components);
        this.textFactory = textFactory;
        this.textColors = textColors;
        this.builderProvider = builderProvider;
        this.mode = mode;
        this.parentOffset = parentOffset;
        ImmutableList.Builder<FoxPathSection> builder = ImmutableList.builder();
        for (FoxPathSection component : components) {
            if (component != null) {
                if (this.indexPathSection == null && component instanceof IndexPathSection) {
                    this.indexPathSection = (IndexPathSection) component;
                } else if (this.objectPathSection == null && component instanceof ObjectPathSection) {
                    this.objectPathSection = ((ObjectPathSection) component);
                } else if (this.linkPathSection == null && component instanceof LinkPathSection) {
                    this.linkPathSection = ((LinkPathSection) component);
                } else {
                    builder.add(component);
                }
            }
        }
        this.extras = builder.build();
    }

    @Nonnull
    @Override
    public FoxPathExtImpl resolve(@Nullable FoxPath path, ResolveConfig config) {
        if (path == null || path.isEmpty()) return this;
        Mode mode = Mode.DEFAULT;
        int otherOffset = 0;
        IndexPathSection otherIndex = null;
        ObjectPathSection otherObject = null;
        LinkPathSection otherLink = null;
        if (path instanceof FoxPathExt) {
            FoxPathExt pathExt = ((FoxPathExt) path);
            mode = pathExt.getMode();
            otherOffset = pathExt.getParentOffset();
            otherIndex = pathExt.getIndexSection().orElse(null);
            otherObject = pathExt.getObjectSection().orElse(null);
            otherLink = pathExt.getLinkSection().orElse(null);
        } else {
            // TODO add handling for non-EXT paths
            // while not strictly necessary, it makes me feel better about the future.
        }

        if (otherIndex != null && otherObject != null && mode == Mode.LINK) mode = Mode.DEFAULT;

        if (mode == Mode.DEFAULT) mode = config.get(FoxPathExt.DEFAULT_ABSOLUTE) ? Mode.ABSOLUTE : Mode.RELATIVE;

        LinkResolve linkResolve = config.get(FoxPathExt.LINK_RESOLVE);

        IndexPathSection newIndex = this.indexPathSection;
        ObjectPathSection newObject = this.objectPathSection;
        LinkPathSection newLink = this.linkPathSection;
        int newOffset = this.parentOffset;
        Mode newMode = this.mode;

        if (mode == Mode.ABSOLUTE) newMode = Mode.ABSOLUTE;
        else if (mode == Mode.HOME) newMode = Mode.HOME;

        if (otherIndex != null) newIndex = otherIndex;
        if (otherObject != null) {
            if (linkResolve != LinkResolve.APPEND || this.linkPathSection == null) {
                if (newObject == null || mode == Mode.ABSOLUTE || mode == Mode.HOME) {
                    newObject = otherObject;
                    if (mode == Mode.ABSOLUTE || mode == Mode.HOME) {
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
                    newObject = ObjectPathSection.from(parts);
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
                    newLink = LinkPathSection.of(parts);
                    break;
            }
        } else {
            newOffset += otherOffset;
        }

        FoxPathExtImpl.Builder newPathBuilder = builderProvider.get();

        newPathBuilder
                .indexSection(newIndex)
                .objectSection(newObject)
                .linkSection(newLink)
                .parentOffset(newOffset)
                .mode(newMode);


        return newPathBuilder.build();
    }

    @Override
    public FoxPathExt asMode(Mode mode) {
        return new FoxPathExtImpl(textFactory, textColors, builderProvider, components, mode, parentOffset);
    }

    @Override
    public Optional<IndexPathSection> getIndexSection() {
        return Optional.ofNullable(this.indexPathSection);
    }

    @Override
    public Optional<ObjectPathSection> getObjectSection() {
        return Optional.ofNullable(this.objectPathSection);
    }

    @Override
    public Optional<LinkPathSection> getLinkSection() {
        return Optional.ofNullable(this.linkPathSection);
    }

    public List<FoxPathSection> getExtras() {
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Iterator<FoxPathSection> it = this.components.iterator(); it.hasNext(); ) {
            FoxPathSection component = it.next();
            if (component instanceof ObjectPathSection) {
                if (this.mode == Mode.ABSOLUTE) builder.append("/");
                else if (this.mode == Mode.RELATIVE && this.parentOffset == 0) builder.append("./");
                for (int i = 0; i < parentOffset; i++) {
                    builder.append("../");
                }
            }
            builder.append(component);
            if (it.hasNext())
                builder.append(':');
        }
        return builder.toString();
    }

    @Override
    public Text toText() {
        Text.Builder builder = textFactory.builder();
        for (Iterator<FoxPathSection> it = this.components.iterator(); it.hasNext(); ) {
            FoxPathSection section = it.next();
            if (section instanceof IndexPathSection) {
                IndexPathSection indexSection = ((IndexPathSection) section);
                builder.append(textFactory.of(textColors.LIGHT_PURPLE, '@', textColors.GREEN, indexSection.getIndex()));
                StandardPathComponent namespacePath = indexSection.getNamespacePath();
                if (namespacePath != null)
                    for (String namespaceElement : namespacePath.getElements()) {
                        builder.append(textFactory.of(textColors.YELLOW, '/', textColors.RESET, namespaceElement));
                    }
            } else if (section instanceof ObjectPathSection) {
                ObjectPathSection objectPathSection = ((ObjectPathSection) section);
                StringBuilder sb = new StringBuilder();
                if (this.mode == Mode.ABSOLUTE) sb.append("/");
                else if (this.mode == Mode.RELATIVE && this.parentOffset == 0) sb.append("./");
                for (int i = 0; i < parentOffset; i++) {
                    sb.append("../");
                }
                builder.append(textFactory.of(textColors.GRAY, sb.toString()));
                for (Iterator<String> it2 = objectPathSection.getElements().iterator(); it2.hasNext(); ) {
                    builder.append(textFactory.of(textColors.RESET, it2.next()));
                    if (it2.hasNext()) builder.append(textFactory.of(textColors.YELLOW, '/'));
                }
            } else if (section instanceof LinkPathSection) {
                LinkPathSection linkSection = ((LinkPathSection) section);
                for (Iterator<StandardPathComponent> it2 = linkSection.getLinkComponents().iterator(); it2.hasNext(); ) {
                    StandardPathComponent link = it2.next();
                    for (Iterator<String> it3 = link.getElements().iterator(); it3.hasNext(); ) {
                        builder.append(textFactory.of(textColors.RESET, it3.next()));
                        if (it3.hasNext()) builder.append(textFactory.of(textColors.YELLOW, '/'));
                    }
                    if (it2.hasNext()) builder.append(textFactory.of(textColors.AQUA, ":"));
                }
            } else {
                builder.append(textFactory.of(textColors.RESET, section));
            }
            if (it.hasNext())
                builder.append(textFactory.of(textColors.LIGHT_PURPLE, ':'));
        }
        return builder.build();
    }

    public static class Builder implements FoxPathExt.Builder {

        private final TextFactory textFactory;
        private final TextColors textColors;
        private final Provider<Builder> builderProvider;

        @Nullable
        private IndexPathSection indexSection;
        @Nullable
        private ObjectPathSection objectSection;
        @Nullable
        private LinkPathSection linkSection;

        private List<FoxPathSection> extraSections = new ArrayList<>();

        @Nonnull
        private Mode mode = Mode.DEFAULT;
        private int parentOffset = 0;

        @Inject
        private Builder(TextFactory textFactory, TextColors textColors, Provider<Builder> builderProvider) {
            this.textFactory = textFactory;
            this.textColors = textColors;
            this.builderProvider = builderProvider;
        }

        @Override
        @Nonnull
        public FoxPathExt.Builder indexSection(@Nullable IndexPathSection section) {
            this.indexSection = section;
            return this;
        }

        @Override
        @Nonnull
        public FoxPathExt.Builder objectSection(@Nullable ObjectPathSection section) {
            this.objectSection = section;
            return this;
        }

        @Override
        @Nonnull
        public FoxPathExt.Builder linkSection(@Nullable LinkPathSection section) {
            this.linkSection = section;
            return this;
        }

        @Nonnull
        @Override
        public FoxPathExt.Builder addSection(@Nullable FoxPathSection section) {
            if (section instanceof IndexPathSection) {
                return indexSection((IndexPathSection) section);
            } else if (section instanceof ObjectPathSection) {
                return objectSection(((ObjectPathSection) section));
            } else if (section instanceof LinkPathSection) {
                return linkSection(((LinkPathSection) section));
            } else {
                this.extraSections.add(section);
                return this;
            }
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
            ImmutableList.Builder<FoxPathSection> builder = ImmutableList.builder();
            if (indexSection != null) builder.add(indexSection);
            if (objectSection != null) builder.add(objectSection);
            if (linkSection != null) builder.add(linkSection);
            builder.addAll(this.extraSections);
            return new FoxPathExtImpl(textFactory, textColors, builderProvider, builder.build(), mode, parentOffset);
        }
    }
}
