package net.foxdenstudio.foxcore.api.path;

import net.foxdenstudio.foxcore.api.path.component.FoxPathComponent;
import net.foxdenstudio.foxcore.api.path.resolve.ResolveConfig;

import javax.annotation.Nonnull;
import java.util.List;

public interface FoxPath {

    @Nonnull
    default FoxPath resolve(FoxPath path) {
        return resolve(path, new ResolveConfig());
    }

    @Nonnull
    FoxPath resolve(FoxPath path, ResolveConfig config);

    @Nonnull
    List<FoxPathComponent> getComponents();

    boolean isEmpty();

    boolean equals(Object o);

    int hashCode();

    String toString();

}
