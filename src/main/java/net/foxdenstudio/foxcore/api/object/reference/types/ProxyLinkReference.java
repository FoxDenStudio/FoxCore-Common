package net.foxdenstudio.foxcore.api.object.reference.types;

import sun.awt.image.ImageWatched;

public interface ProxyLinkReference extends LinkReference, ProxyReference {

    @Override
    default boolean isEmbedded() {
        return false;
    }
}
