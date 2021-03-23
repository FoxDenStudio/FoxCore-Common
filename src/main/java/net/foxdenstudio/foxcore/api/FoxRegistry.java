package net.foxdenstudio.foxcore.api;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.archetype.type.FoxType;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.generator.GeneratorObject;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.storage.FoxObjectData;
import net.foxdenstudio.foxcore.api.storage.ISimpleState;

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
