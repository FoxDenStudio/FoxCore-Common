package net.foxdenstudio.foxcore.api.object.index;

import net.foxdenstudio.foxcore.api.path.components.FoxNamespacePath;

import java.util.Optional;

public interface FoxObjectIndex extends Namespace{

    Optional<Namespace> getNamespace(FoxNamespacePath indexPath);

    Namespace getDefaultNamespace();

    void setDefaultNamespace(FoxNamespacePath indexPath);

    default void setDefaultNamespace(Namespace namespace){
        setDefaultNamespace(namespace.getNamespacePath());
    }

    String getIndexName();
}
