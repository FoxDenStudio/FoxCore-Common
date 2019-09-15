package net.foxdenstudio.foxcore.api.region;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.object.FoxObjectBase;

public abstract class FoxRegionBase<T extends FoxRegionBase<T>> extends FoxObjectBase<T> implements FoxRegion<T> {

    protected FoxRegionBase(FoxArchetype archetype, FoxAttribute<?>... attributes) {
        super(archetype, attributes);
    }

}
