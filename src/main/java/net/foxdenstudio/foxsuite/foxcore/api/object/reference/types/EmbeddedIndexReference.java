package net.foxdenstudio.foxsuite.foxcore.api.object.reference.types;

import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxsuite.foxcore.api.path.section.LinkPathSection;

public interface EmbeddedIndexReference extends IndexReference {

    default boolean isEmbedded() {
        return true;
    }

    EmbedCapableIndexReference getParent();

    StandardPathComponent getEmbedPath();

    LinkPathSection getFullEmbedPath();
}
