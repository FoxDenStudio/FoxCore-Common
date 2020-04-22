package net.foxdenstudio.foxcore.api.object.link.node;

import net.foxdenstudio.foxcore.api.object.link.LinkSchema;

public interface LinkLeafNode extends LinkNode {

    @Override
    default boolean freeStructure() {
        return false;
    }

    @Override
    default boolean supportsEmbedding() {
        return false;


    }
}
