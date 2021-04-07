package net.foxdenstudio.foxsuite.foxcore.api.object.reference.types;

public interface ProxyLinkReference extends LinkReference, ProxyReference {

    @Override
    default boolean isEmbedded() {
        return false;
    }
}
