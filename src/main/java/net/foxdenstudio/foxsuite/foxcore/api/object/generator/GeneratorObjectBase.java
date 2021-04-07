package net.foxdenstudio.foxsuite.foxcore.api.object.generator;

import net.foxdenstudio.foxsuite.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObjectBase;
import net.foxdenstudio.foxsuite.foxcore.content.archetype.GeneratorArchetype;

import javax.inject.Singleton;

@Singleton
public abstract class GeneratorObjectBase<G extends FoxObject> extends FoxObjectBase<GeneratorArchetype> implements GeneratorObject<G> {

    protected GeneratorObjectBase(GeneratorArchetype archetype, FoxAttribute<?>... attributes) {
        super(archetype, attributes);
    }



}
