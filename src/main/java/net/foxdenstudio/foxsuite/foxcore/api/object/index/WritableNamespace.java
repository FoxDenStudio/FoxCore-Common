package net.foxdenstudio.foxsuite.foxcore.api.object.index;

import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.IndexReference;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxsuite.foxcore.api.path.section.ObjectPathSection;

import java.util.Optional;

public interface WritableNamespace extends Namespace {

    Optional<IndexReference> addObject(FoxObject foxObject, ObjectPathSection path);

    default boolean isPathValid(ObjectPathSection section, boolean write) {
        return isPathValid(section.getPathComponent(), write);
    }

    default boolean isPathValid(StandardPathComponent component, boolean write) {
        return true;
    }

}
