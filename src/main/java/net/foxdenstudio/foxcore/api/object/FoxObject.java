package net.foxdenstudio.foxcore.api.object;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.path.components.FoxLinkPath;
import net.foxdenstudio.foxcore.api.property.FoxProperty;
import net.foxdenstudio.foxcore.api.object.link.LinkResult;

import javax.annotation.Nonnull;
import java.util.Set;

public interface FoxObject {

    Set<FoxArchetype> getArchetypes();

    FoxArchetype getPrimaryArchetype();

    Set<FoxAttribute> getAttributes();

    <P extends FoxProperty<?, A>, A extends FoxAttribute<P>> P getProperty(A attribute);

    <P extends FoxProperty<?, A>, A extends FoxAttribute<P>> P getOrCreateProperty(A attribute);

    boolean hasAttribute(FoxAttribute<?> attribute);

    LinkResult link(@Nonnull FoxObject object, @Nonnull FoxLinkPath path);

    Set<FoxObject> getLinks(@Nonnull FoxLinkPath path);

}
