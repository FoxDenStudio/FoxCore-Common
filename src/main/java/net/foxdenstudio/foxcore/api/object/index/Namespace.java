package net.foxdenstudio.foxcore.api.object.index;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;
import net.foxdenstudio.foxcore.api.path.section.IndexPathSection;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.path.section.ObjectPathSection;

import java.util.Collection;
import java.util.Optional;

public interface Namespace {

    default Optional<IndexReference> getObjectReference(ObjectPathSection path){
        return getObjectReference(path.getPathComponent());
    }

    Optional<IndexReference> getObjectReference(StandardPathComponent path);

    default Optional<FoxObject> getObject(StandardPathComponent path) {
        return this.getObjectReference(path).flatMap(IndexReference::getObject);
    }

    default Optional<FoxObject> getObject(ObjectPathSection path) {
        return this.getObjectReference(path.getPathComponent()).flatMap(IndexReference::getObject);
    }

    Collection<StandardPathComponent> getAllObjectPaths();

    IndexPathSection getIndexPath();

    default boolean isWritable() {
        return this instanceof WritableNamespace;
    }

}
