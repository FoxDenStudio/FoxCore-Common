package net.foxdenstudio.foxcore.impl.object.index.types;

import net.foxdenstudio.foxcore.api.object.index.WritableIndexBase;
import net.foxdenstudio.foxcore.api.object.index.types.FileIndex;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;

import javax.inject.Inject;

public class FileIndexImpl extends WritableIndexBase implements FileIndex {

    @Inject
    private FileIndexImpl(FoxPathFactory pathFactory) {
        super(pathFactory);
    }

    @Override
    protected void registerNamespaces() {
        registerNamespace(new FileIndexNamespace(), null);
    }

    @Override
    public void save() {

    }

    @Override
    public void load() {

    }

    protected class FileIndexNamespace extends NamespaceBase {

    }

}
