package net.foxdenstudio.foxcore.api;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.object.generator.GeneratorObject;

public interface FoxRegistry {

    boolean registerArchetype(FoxArchetype archetype);

    boolean registerGenerator(String path, GeneratorObject<?> generator);
}
