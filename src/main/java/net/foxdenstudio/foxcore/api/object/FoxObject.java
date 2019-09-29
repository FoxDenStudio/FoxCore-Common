package net.foxdenstudio.foxcore.api.object;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.attribute.holder.AttributeHolder;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;

import java.util.Optional;

public interface FoxObject extends AttributeHolder {

    FoxArchetype getArchetype();

    Optional<IndexReference> getIndexReference();

    void setIndexReference(IndexReference indexReference);

    //LinkResult link(@Nonnull FoxObject object, @Nonnull FoxLinkPath path);

    //Set<FoxObject> getLinks(@Nonnull FoxLinkPath path);

}
