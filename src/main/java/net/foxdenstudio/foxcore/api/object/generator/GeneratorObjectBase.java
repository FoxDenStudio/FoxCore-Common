package net.foxdenstudio.foxcore.api.object.generator;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.FoxObjectBase;
import net.foxdenstudio.foxcore.content.archetype.GeneratorArchetype;

import javax.inject.Singleton;

@Singleton
public abstract class GeneratorObjectBase<G extends FoxObject> extends FoxObjectBase<GeneratorArchetype> implements GeneratorObject<G> {

    protected GeneratorObjectBase(GeneratorArchetype archetype, FoxAttribute<?>... attributes) {
        super(archetype, attributes);
    }



}
