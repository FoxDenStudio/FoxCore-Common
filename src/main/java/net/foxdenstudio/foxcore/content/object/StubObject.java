package net.foxdenstudio.foxcore.content.object;

import net.foxdenstudio.foxcore.api.object.FoxObjectBase;
import net.foxdenstudio.foxcore.content.archetype.FoxObjectArchetype;

import javax.inject.Inject;

public class StubObject extends FoxObjectBase {

    @Inject
    private StubObject(FoxObjectArchetype archetype) {
        super(archetype);
    }
}
