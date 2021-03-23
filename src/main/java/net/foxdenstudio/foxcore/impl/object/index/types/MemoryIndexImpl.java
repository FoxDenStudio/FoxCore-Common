package net.foxdenstudio.foxcore.impl.object.index.types;

import net.foxdenstudio.foxcore.api.object.index.WritableIndexBase;
import net.foxdenstudio.foxcore.api.object.index.types.MemoryIndex;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;

import javax.inject.Inject;

public class MemoryIndexImpl extends WritableIndexBase implements MemoryIndex {

    @Inject
    private MemoryIndexImpl(FoxPathFactory pathFactory) {
        super(pathFactory);
    }

    @Override
    protected void registerNamespaces() {

    }

    @Override
    public String getIndexName() {
        return "mem";
    }
}
