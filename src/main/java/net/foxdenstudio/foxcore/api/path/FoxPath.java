package net.foxdenstudio.foxcore.api.path;

import net.foxdenstudio.foxcore.api.path.section.FoxPathSection;
import net.foxdenstudio.foxcore.api.path.resolve.ResolveConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface FoxPath {

    @Nonnull
    default FoxPath resolve(@Nullable FoxPath path) {
        return resolve(path, new ResolveConfig());
    }

    @Nonnull
    FoxPath resolve(@Nullable FoxPath path, ResolveConfig config);

    @Nonnull
    List<FoxPathSection> getSections();

    boolean isEmpty();

    boolean equals(Object o);

    int hashCode();

    String toString();

}
