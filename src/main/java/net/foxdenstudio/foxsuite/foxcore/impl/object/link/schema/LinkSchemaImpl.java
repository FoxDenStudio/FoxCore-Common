package net.foxdenstudio.foxsuite.foxcore.impl.object.link.schema;

import net.foxdenstudio.foxsuite.foxcore.api.object.link.node.FoxBasicLinkNodeContainer;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.node.LinkNode;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.node.LinkNodeContainer;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.schema.LinkSchema;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;

import javax.inject.Inject;

public class LinkSchemaImpl implements LinkSchema {

    private final LinkNodeContainer container;

    @Inject
    public LinkSchemaImpl() {
        this.container = new FoxBasicLinkNodeContainer(null);
    }

    @Override
    public void populate(LinkNodeContainer nodeContainer) {

    }

    @Override
    public void addNode(StandardPathComponent path, LinkNode node) {

    }
}
