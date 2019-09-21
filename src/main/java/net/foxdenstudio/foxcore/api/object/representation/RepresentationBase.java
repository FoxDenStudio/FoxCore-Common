package net.foxdenstudio.foxcore.api.object.representation;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.object.FoxObjectBase;

public abstract class RepresentationBase<T extends RepresentationBase<T,R>, R extends FoxRepresentable<T, R>>
        extends FoxObjectBase<T>
        implements RepresentationObject<T, R> {

    protected final R represented;

    protected RepresentationBase(R represented, FoxArchetype archetype, FoxAttribute<?>... attributes) {
        super(archetype, attributes);
        this.represented = represented;
    }

    @Override
    public R getRepresentedObject() {
        return this.represented;
    }
}
