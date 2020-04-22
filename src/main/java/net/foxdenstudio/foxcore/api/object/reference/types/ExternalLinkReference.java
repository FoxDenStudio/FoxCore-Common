package net.foxdenstudio.foxcore.api.object.reference.types;

public interface ExternalLinkReference extends LinkReference, LazyReference {

    @Override
    default boolean isEmbedded() {
        return false;
    }
}
