package net.foxdenstudio.foxcore.api.object.link;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.link.node.LinkNode;

public interface LinkSchema {

    LinkNode createNodeInstance(FoxObject object);
}
