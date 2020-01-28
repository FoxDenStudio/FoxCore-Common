package net.foxdenstudio.foxcore.api.object.index;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.path.section.ObjectPathSection;

import java.util.Optional;

public interface WritableNamespace extends Namespace {

    Optional<IndexReference> addObject(FoxObject foxObject, ObjectPathSection path);

}
