package net.foxdenstudio.foxcore.api.object.reference;

public interface EmbeddedLinkReference extends LinkReference, EmbeddedIndexReference {

    @Override
    default boolean unlink() {
        return false;
    }

    @Override
    default boolean isEmbedded() {
        return true;
    }
}
