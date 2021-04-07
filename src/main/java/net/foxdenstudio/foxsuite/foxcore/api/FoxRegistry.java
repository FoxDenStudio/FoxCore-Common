package net.foxdenstudio.foxsuite.foxcore.api;

import net.foxdenstudio.foxsuite.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxsuite.foxcore.api.archetype.type.FoxType;
import net.foxdenstudio.foxsuite.foxcore.api.object.generator.GeneratorObject;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxsuite.foxcore.api.storage.FoxObjectData;
import net.foxdenstudio.foxsuite.foxcore.api.storage.ISimpleState;

import java.util.Map;
import java.util.Optional;

public interface FoxRegistry {

    Optional<String> registerArchetype(FoxArchetype archetype);

    boolean registerGenerator(StandardPathComponent path, GeneratorObject<?> generator);

    <T extends ISimpleState<D>, D extends FoxObjectData>
    boolean simpleMajorTypeRegistration(FoxType archetype,
                                        Class<T> objectClass,
                                        Class<D> dataClass,
                                        Map<StandardPathComponent, GeneratorObject<T>> generators);
}
