package net.foxdenstudio.foxsuite.foxcore.api.object.link.schema;

import net.foxdenstudio.foxsuite.foxcore.api.object.link.node.LinkNode;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.node.LinkNodeContainer;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;

public interface LinkSchema {

    void populate(LinkNodeContainer nodeContainer);

    void addNode(StandardPathComponent path, LinkNode node);

}
