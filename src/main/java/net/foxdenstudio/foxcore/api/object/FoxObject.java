package net.foxdenstudio.foxcore.api.object;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.attribute.holder.AttributeHolder;
import net.foxdenstudio.foxcore.api.object.link.LinkResult;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

public interface FoxObject extends AttributeHolder {

    FoxArchetype getArchetype();

    Optional<IndexReference> getIndexReference();

    void setIndexReference(IndexReference indexReference);

    //LinkResult link(@Nonnull FoxObject object, @Nullable StandardPathComponent linkPath);

    //Set<FoxObject> getLinks(@Nullable StandardPathComponent path);

}
