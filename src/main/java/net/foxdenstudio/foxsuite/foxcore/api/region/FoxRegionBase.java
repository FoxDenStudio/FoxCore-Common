package net.foxdenstudio.foxsuite.foxcore.api.region;

import net.foxdenstudio.foxsuite.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxsuite.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObjectBase;

public abstract class FoxRegionBase<A extends FoxArchetype> extends FoxObjectBase<A> implements FoxRegion {

    protected FoxRegionBase(A archetype, FoxAttribute<?>... attributes) {
        super(archetype, attributes);
    }

}
