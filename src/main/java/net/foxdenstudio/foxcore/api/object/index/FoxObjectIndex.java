package net.foxdenstudio.foxcore.api.object.index;

import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import java.util.Optional;

public interface FoxObjectIndex extends Namespace {

    Optional<Namespace> getNamespace(StandardPathComponent indexPath);

    Namespace getDefaultNamespace();

    boolean setDefaultNamespace(StandardPathComponent indexPath);

    String getIndexName();
}
