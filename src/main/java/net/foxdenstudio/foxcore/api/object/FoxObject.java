package net.foxdenstudio.foxcore.api.object;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.attribute.holder.AttributeHolder;
import net.foxdenstudio.foxcore.api.path.components.FoxLinkPath;
import net.foxdenstudio.foxcore.api.object.link.LinkResult;

import javax.annotation.Nonnull;
import java.util.Set;

public interface FoxObject extends AttributeHolder {

    Set<FoxArchetype> getArchetypes();

    FoxArchetype getPrimaryArchetype();

    LinkResult link(@Nonnull FoxObject object, @Nonnull FoxLinkPath path);

    Set<FoxObject> getLinks(@Nonnull FoxLinkPath path);

}
