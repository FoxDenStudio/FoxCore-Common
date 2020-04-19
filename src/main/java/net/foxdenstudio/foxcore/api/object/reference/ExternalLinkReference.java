package net.foxdenstudio.foxcore.api.object.reference;

public interface ExternalLinkReference extends LinkReference, LazyReference {

    @Override
    default boolean isEmbedded() {
        return false;
    }
}
