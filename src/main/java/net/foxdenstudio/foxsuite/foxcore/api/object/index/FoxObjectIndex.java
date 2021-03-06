package net.foxdenstudio.foxsuite.foxcore.api.object.index;

import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface FoxObjectIndex extends Namespace {

    Optional<Namespace> getNamespace(@Nonnull StandardPathComponent indexPath);

    Namespace getDefaultNamespace();

    boolean setDefaultNamespace(StandardPathComponent indexPath);

    String getIndexName();
}
