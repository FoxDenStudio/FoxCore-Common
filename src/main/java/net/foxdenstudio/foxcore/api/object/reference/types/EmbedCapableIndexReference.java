package net.foxdenstudio.foxcore.api.object.reference.types;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import java.util.Map;

public interface EmbedCapableIndexReference extends IndexReference {

    Map<StandardPathComponent, EmbeddedIndexReference> getEmbeddedObjects();

    boolean contains(FoxObject foxObject);
}
