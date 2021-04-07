package net.foxdenstudio.foxsuite.foxcore.impl;

import net.foxdenstudio.foxsuite.foxcore.api.FoxRegistry;
import net.foxdenstudio.foxsuite.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxsuite.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxsuite.foxcore.api.archetype.type.FoxType;
import net.foxdenstudio.foxsuite.foxcore.api.object.generator.GeneratorObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.WritableNamespace;
import net.foxdenstudio.foxsuite.foxcore.api.object.representation.SimpleRepresentation;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxsuite.foxcore.api.path.section.ObjectPathSection;
import net.foxdenstudio.foxsuite.foxcore.api.storage.FoxObjectData;
import net.foxdenstudio.foxsuite.foxcore.api.storage.ISimpleState;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.*;

public class FoxRegistryImpl implements FoxRegistry {

    private static final StandardPathComponent GEN_PATH = StandardPathComponent.of("gen");

    private final SimpleRepresentation.Factory srFactory;
    private final FoxMainIndex mainIndex;
    private final WritableNamespace target;

    private final Map<String, FoxArchetype> archetypeMap = new HashMap<>();
    private final Map<String, MajorType<?,?>> majorTypes = new HashMap<>();

    @FoxLogger("registry")
    Logger logger;

    @Inject
    private FoxRegistryImpl(SimpleRepresentation.Factory srFactory, FoxMainIndex mainIndex) {
        this.srFactory = srFactory;
        this.mainIndex = mainIndex;
        this.target = mainIndex.getDefaultObjectIndex();
    }

    @Override
    public Optional<String> registerArchetype(FoxArchetype archetype) {
        if (this.archetypeMap.containsValue(archetype)) return Optional.empty();

        Set<FoxArchetype> discovered = new HashSet<>();
        Deque<FoxArchetype> eval = new LinkedList<>();
        eval.push(archetype);
        while (!eval.isEmpty()) {
            FoxArchetype arch = eval.pop();
            if (!this.archetypeMap.containsValue(arch) && discovered.add(arch)) {
                eval.addAll(arch.getAllArchetypes());
            }
        }
        String archName = null;
        for (FoxArchetype arch : discovered) {
            String baseName = arch.getType();
            int count = 1;
            String name = baseName;
            while (this.archetypeMap.containsKey(name)) {
                name = baseName + "$" + (count++);
            }
            this.archetypeMap.put(name, arch);
            if(arch == archetype) archName = name;
            SimpleRepresentation<FoxArchetype> rep = srFactory.newRepresentation(arch);
            ObjectPathSection path = ObjectPathSection.of("arch", name);
            this.target.addObject(rep, path);
            arch.setRepresentation(rep);
        }
        return Optional.ofNullable(archName);
    }

    @Override
    public boolean registerGenerator(StandardPathComponent path, GeneratorObject<?> generator) {
        // TODO do something else here maybe?

        return this.target.addObject(generator, ObjectPathSection.from(GEN_PATH.concat(path))).isPresent();
    }

    @Override
    public <T extends ISimpleState<D>, D extends FoxObjectData>
    boolean simpleMajorTypeRegistration(FoxType type,
                                        Class<T> objectClass,
                                        Class<D> dataClass,
                                        Map<StandardPathComponent, GeneratorObject<T>> generators) {

        Optional<String> typeNameOpt = this.registerArchetype(type);
        if(!typeNameOpt.isPresent()) return false;

        MajorType<T,D> typeEntry = new MajorType<>(type, objectClass, dataClass, generators);
        String name = typeNameOpt.get();

        this.majorTypes.put(name, typeEntry);

        for(Map.Entry<StandardPathComponent, GeneratorObject<T>> gen : generators.entrySet()){
            boolean success = this.registerGenerator(gen.getKey(), gen.getValue());
            if(!success) {
                logger.warn("Unable to register generator \"" + gen.getKey() + "\" for major type \"" + name + "\"!");
            }
        }

        return true;
    }

    static class MajorType <T extends ISimpleState<D>, D extends FoxObjectData>{
        FoxType type;
        Class<T> objectClass;
        Class<D> dataClass;
        Map<StandardPathComponent, GeneratorObject<T>> generators;

        public MajorType(FoxType type, Class<T> objectClass, Class<D> dataClass, Map<StandardPathComponent, GeneratorObject<T>> generators) {
            this.type = type;
            this.objectClass = objectClass;
            this.dataClass = dataClass;
            this.generators = generators;
        }
    }

}
