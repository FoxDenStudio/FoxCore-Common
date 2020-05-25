package net.foxdenstudio.foxcore.api.object.reference.types;

public interface InternalLinkReference extends LinkReference, ProxyReference {

    @Override
    default boolean isEmbedded() {
        return false;
    }
}
