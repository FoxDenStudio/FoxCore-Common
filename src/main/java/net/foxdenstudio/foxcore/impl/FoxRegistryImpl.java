package net.foxdenstudio.foxcore.impl;

import net.foxdenstudio.foxcore.api.FoxRegistry;
import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.object.generator.GeneratorObject;
import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.object.index.WritableNamespace;
import net.foxdenstudio.foxcore.api.object.representation.SimpleRepresentation;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.path.section.ObjectPathSection;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.*;

public class FoxRegistryImpl implements FoxRegistry {

    private final SimpleRepresentation.Factory srFactory;
    private final FoxMainIndex mainIndex;
    private final WritableNamespace target;

    private final Map<String, FoxArchetype> archetypeMap = new HashMap<>();

    @FoxLogger("registry")
    Logger logger;

    @Inject
    private FoxRegistryImpl(SimpleRepresentation.Factory srFactory, FoxMainIndex mainIndex) {
        this.srFactory = srFactory;
        this.mainIndex = mainIndex;
        this.target = mainIndex.getDefaultObjectIndex();
    }

    @Override
    public boolean registerArchetype(FoxArchetype archetype) {
        if (this.archetypeMap.containsValue(archetype)) return false;

        Set<FoxArchetype> discovered = new HashSet<>();
        List<FoxArchetype> eval = new ArrayList<>();
        eval.add(archetype);
        while (!eval.isEmpty()) {
            FoxArchetype arch = eval.remove(0);
            if (!this.archetypeMap.containsValue(arch) && discovered.add(arch)) {
                eval.addAll(arch.getAllArchetypes());
            }
        }
        for (FoxArchetype arch : discovered) {
            String baseName = arch.getType();
            int count = 1;
            String name = baseName;
            while (this.archetypeMap.containsKey(name)) {
                name = baseName + "$" + (count++);
            }
            this.archetypeMap.put(name, arch);
            SimpleRepresentation<FoxArchetype> rep = srFactory.newRepresentation(arch);
            ObjectPathSection path = ObjectPathSection.of("arch", name);
            this.target.addObject(rep, path);
            arch.setRepresentation(rep);
        }
        return true;
    }

    @Override
    public boolean registerGenerator(String path, GeneratorObject<?> generator) {
        return false;
    }

}
