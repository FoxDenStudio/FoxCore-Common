package net.foxdenstudio.foxcore.api.region;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.object.FoxObjectBase;

public abstract class FoxRegionBase<A extends FoxArchetype> extends FoxObjectBase<A> implements FoxRegion {

    protected FoxRegionBase(A archetype, FoxAttribute<?>... attributes) {
        super(archetype, attributes);
    }

}
