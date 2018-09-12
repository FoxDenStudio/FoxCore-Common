package net.foxdenstudio.foxcore.common.object.index;

import net.foxdenstudio.foxcore.common.object.FoxObject;
import net.foxdenstudio.foxcore.common.path.FoxPath;

import java.util.Optional;

public interface Namespace<T extends FoxObject> {

    Optional<T> getObject(FoxPath path);

}
