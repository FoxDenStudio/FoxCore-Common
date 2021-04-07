package net.foxdenstudio.foxsuite.foxcore.api.object.index;

import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.IndexReference;
import net.foxdenstudio.foxsuite.foxcore.api.path.section.IndexPathSection;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxsuite.foxcore.api.path.section.LinkPathSection;
import net.foxdenstudio.foxsuite.foxcore.api.path.section.ObjectPathSection;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

public interface Namespace {

    default Optional<IndexReference> getObjectReference(ObjectPathSection path){
        return getObjectReference(path.getPathComponent());
    }

    default Optional<IndexReference> getObjectReference(ObjectPathSection path, @Nullable LinkPathSection links){
        return getObjectReference(path.getPathComponent(), links);
    }

    default Optional<IndexReference> getObjectReference(StandardPathComponent path) {
        return getObjectReference(path, null);
    }

    Optional<IndexReference> getObjectReference(StandardPathComponent path, @Nullable LinkPathSection links);


    default Optional<FoxObject> getObject(StandardPathComponent path) {
        return this.getObjectReference(path).flatMap(IndexReference::getObject);
    }

    default Optional<FoxObject> getObject(StandardPathComponent path, @Nullable LinkPathSection links) {
        return this.getObjectReference(path, links).flatMap(IndexReference::getObject);
    }

    default Optional<FoxObject> getObject(ObjectPathSection path) {
        return this.getObjectReference(path.getPathComponent()).flatMap(IndexReference::getObject);
    }

    default Optional<FoxObject> getObject(ObjectPathSection path, @Nullable LinkPathSection links) {
        return this.getObjectReference(path.getPathComponent(), links).flatMap(IndexReference::getObject);
    }

    Collection<StandardPathComponent> getAllObjectPaths();

    IndexPathSection getIndexPath();

    default boolean isWritable() {
        return this instanceof WritableNamespace;
    }

    boolean contains(FoxObject foxObject);

}
