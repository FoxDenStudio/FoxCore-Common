package net.foxdenstudio.foxsuite.foxcore.api.object.representation;

import net.foxdenstudio.foxsuite.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxsuite.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObjectBase;

public abstract class RepresentationBase<R extends FoxRepresentable, A extends FoxArchetype> extends FoxObjectBase<A> implements RepresentationObject<R> {

    protected final R represented;

    protected RepresentationBase(R represented, A archetype, FoxAttribute<?>... attributes) {
        super(archetype, attributes);
        this.represented = represented;
    }

    @Override
    public R getRepresentedObject() {
        return this.represented;
    }
}
