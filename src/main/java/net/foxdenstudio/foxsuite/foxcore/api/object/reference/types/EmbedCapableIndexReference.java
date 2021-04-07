package net.foxdenstudio.foxsuite.foxcore.api.object.reference.types;

import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;

import java.util.Map;

public interface EmbedCapableIndexReference extends IndexReference {

    Map<StandardPathComponent, EmbeddedIndexReference> getEmbeddedObjects();

    boolean contains(FoxObject foxObject);
}
