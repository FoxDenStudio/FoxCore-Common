package net.foxdenstudio.foxcore.common.object;

import net.foxdenstudio.foxcore.common.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.common.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.common.object.link.LinkResult;
import net.foxdenstudio.foxcore.common.path.FoxPath;
import net.foxdenstudio.foxcore.common.property.FoxProperty;

import javax.annotation.Nullable;
import java.util.Set;

public interface FoxObject {

    Set<FoxArchetype> getArchetypes();

    FoxArchetype getPrimaryArchetype();

    Set<FoxAttribute> getAttributes();

    <P extends FoxProperty<?, A>, A extends FoxAttribute<P>> P getProperty(A attribute);

    LinkResult link(FoxObject object, @Nullable FoxPath path);

}
