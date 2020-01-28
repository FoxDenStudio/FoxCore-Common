package net.foxdenstudio.foxcore.api.path;

import net.foxdenstudio.foxcore.api.path.resolve.ResolveConfig;
import net.foxdenstudio.foxcore.api.path.resolve.ResolveOption;
import net.foxdenstudio.foxcore.api.path.section.FoxPathSection;
import net.foxdenstudio.foxcore.api.path.section.IndexPathSection;
import net.foxdenstudio.foxcore.api.path.section.LinkPathSection;
import net.foxdenstudio.foxcore.api.path.section.ObjectPathSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface FoxPathExt extends FoxPath {

    ResolveOption<Boolean> DEFAULT_ABSOLUTE = ResolveOption.of("defaultAbsolute", false);

    ResolveOption<LinkResolve> LINK_RESOLVE = ResolveOption.of("appendObjectToLinks", LinkResolve.REPLACE);

    Optional<IndexPathSection> getIndexSection();

    Optional<ObjectPathSection> getObjectSection();

    Optional<LinkPathSection> getLinkSection();

    Mode getMode();

    /**
     * The number of directories to navigate upwards when appended to another path.
     * Mode will be relative.
     *
     * @return the offset
     */
    int getParentOffset();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

    @Override
    String toString();

    @Nonnull
    @Override
    default FoxPath resolve(@Nullable FoxPath path) {
        return resolve(path, new ResolveConfig());
    }

    @Nonnull
    @Override
    FoxPath resolve(@Nullable FoxPath path, ResolveConfig config);

    enum Mode {
        RELATIVE, ABSOLUTE, LINK, DEFAULT
    }

    enum LinkResolve {
        REPLACE, APPEND, SPLICE
    }

    interface Builder {

        @Nonnull
        Builder indexSection(@Nullable IndexPathSection section);

        @Nonnull
        Builder objectSection(@Nullable ObjectPathSection section);

        @Nonnull
        Builder linkSection(@Nullable LinkPathSection section);

        @Nonnull
        Builder addSection(@Nullable FoxPathSection section);

        @Nonnull
        Builder mode(@Nonnull Mode mode);

        @Nonnull
        Builder parentOffset(int offset);

        @Nonnull
        FoxPathExt build();
    }
}
