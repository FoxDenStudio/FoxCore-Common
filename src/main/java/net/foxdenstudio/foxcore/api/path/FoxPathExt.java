package net.foxdenstudio.foxcore.api.path;

import net.foxdenstudio.foxcore.api.path.component.IndexPathComponent;
import net.foxdenstudio.foxcore.api.path.component.LinkPathComponent;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.path.resolve.ResolveConfig;
import net.foxdenstudio.foxcore.api.path.resolve.ResolveOption;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface FoxPathExt extends FoxPath {

    ResolveOption<Boolean> DEFAULT_ABSOLUTE = ResolveOption.of("defaultAbsolute", false);

    ResolveOption<LinkResolve> LINK_RESOLVE = ResolveOption.of("appendObjectToLinks", LinkResolve.REPLACE);

    Optional<IndexPathComponent> getIndexComponent();

    Optional<StandardPathComponent> getObjectComponent();

    Optional<LinkPathComponent> getLinkComponent();

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
    FoxPath resolve(FoxPath path);

    @Nonnull
    @Override
    FoxPath resolve(FoxPath path, ResolveConfig config);

    enum Mode {
        RELATIVE, ABSOLUTE, LINK, DEFAULT
    }

    enum LinkResolve{
        REPLACE, APPEND, SPLICE
    }

    interface Builder {

        @Nonnull
        Builder indexComponent(@Nullable IndexPathComponent component);

        @Nonnull
        Builder objectComponent(@Nullable StandardPathComponent component);

        @Nonnull
        Builder linkComponent(@Nullable LinkPathComponent component);

        @Nonnull
        Builder mode(@Nonnull Mode mode);

        @Nonnull
        Builder parentOffset(int offset);

        @Nonnull
        FoxPathExt build();
    }
}
