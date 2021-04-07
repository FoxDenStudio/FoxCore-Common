package net.foxdenstudio.foxsuite.foxcore.api.object.generator;

import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;

public interface GeneratorObject<G extends FoxObject> extends FoxObject, GeneratorCommand<G> {

}
