package net.foxdenstudio.foxcore.api.object.representation;

import com.google.inject.assistedinject.Assisted;
import net.foxdenstudio.foxcore.content.archetype.RepresentationArchetype;

import javax.inject.Inject;

public class SimpleRepresentation<R extends FoxRepresentable> extends RepresentationBase<R> {

    @Inject
    private SimpleRepresentation(RepresentationArchetype representationArchetype,
                                 @Assisted R represented) {
        super(represented, representationArchetype);
    }
}
