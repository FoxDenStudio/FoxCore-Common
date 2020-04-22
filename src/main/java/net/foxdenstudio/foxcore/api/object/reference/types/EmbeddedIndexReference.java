package net.foxdenstudio.foxcore.api.object.reference.types;

import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.path.section.LinkPathSection;

public interface EmbeddedIndexReference extends IndexReference {

    default boolean isEmbedded() {
        return true;
    }

    EmbedCapableIndexReference getParent();

    StandardPathComponent getEmbedPath();

    LinkPathSection getFullEmbedPath();
}
