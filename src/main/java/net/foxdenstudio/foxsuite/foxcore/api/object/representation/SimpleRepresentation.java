package net.foxdenstudio.foxsuite.foxcore.api.object.representation;

import net.foxdenstudio.foxsuite.foxcore.content.archetype.RepresentationArchetype;

import javax.inject.Inject;
import javax.inject.Provider;

public class SimpleRepresentation<R extends FoxRepresentable> extends RepresentationBase<R, RepresentationArchetype> {

    private SimpleRepresentation(RepresentationArchetype representationArchetype,
                                 R represented) {
        super(represented, representationArchetype);
    }

    public static class Factory {
        private final Provider<RepresentationArchetype> representationArchetypeProvider;

        @Inject
        private Factory(Provider<RepresentationArchetype> representationArchetypeProvider) {
            this.representationArchetypeProvider = representationArchetypeProvider;
        }

        public <R extends FoxRepresentable> SimpleRepresentation<R> newRepresentation(R represented){
            return new SimpleRepresentation<>(this.representationArchetypeProvider.get(), represented);
        }
    }
}
