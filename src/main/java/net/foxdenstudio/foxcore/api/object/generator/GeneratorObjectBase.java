package net.foxdenstudio.foxcore.api.object.generator;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.FoxObjectBase;

import javax.inject.Singleton;

@Singleton
public abstract class GeneratorObjectBase<T extends GeneratorObjectBase<T, G>, G extends FoxObject<G>> extends FoxObjectBase<T> implements GeneratorObject<T, G> {

    protected GeneratorObjectBase(FoxArchetype archetype, FoxAttribute<?>... attributes) {
        super(archetype, attributes);
    }

}
