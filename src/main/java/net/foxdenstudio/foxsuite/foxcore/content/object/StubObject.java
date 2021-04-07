package net.foxdenstudio.foxsuite.foxcore.content.object;

import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObjectBase;
import net.foxdenstudio.foxsuite.foxcore.content.archetype.FoxObjectArchetype;

import javax.inject.Inject;

public class StubObject extends FoxObjectBase {

    @Inject
    private StubObject(FoxObjectArchetype archetype) {
        super(archetype);
    }
}
