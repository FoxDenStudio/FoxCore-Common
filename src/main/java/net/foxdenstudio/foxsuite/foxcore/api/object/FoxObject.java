package net.foxdenstudio.foxsuite.foxcore.api.object;

import net.foxdenstudio.foxsuite.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxsuite.foxcore.api.attribute.holder.AttributeHolder;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.LinkContainer;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.LinkResult;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.IndexReference;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface FoxObject extends AttributeHolder {

    FoxArchetype getArchetype();

    Optional<IndexReference> getIndexReference();

    void setIndexReference(IndexReference indexReference);

    LinkResult link(@Nonnull FoxObject object, @Nullable StandardPathComponent linkPath);

    LinkContainer getLinkContainer();

    //Set<FoxObject> getLinks(@Nullable StandardPathComponent path);

}
