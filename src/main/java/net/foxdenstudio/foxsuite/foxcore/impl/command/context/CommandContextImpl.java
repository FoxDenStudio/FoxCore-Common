package net.foxdenstudio.foxsuite.foxcore.impl.command.context;

import com.google.inject.Inject;
import net.foxdenstudio.foxsuite.foxcore.api.command.context.CommandContext;
import net.foxdenstudio.foxsuite.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.Namespace;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.IndexReference;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPathExt;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxsuite.foxcore.api.path.section.IndexPathSection;
import net.foxdenstudio.foxsuite.foxcore.api.path.section.ObjectPathSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CommandContextImpl implements CommandContext {

    private final FoxMainIndex mainIndex;
    private final FoxPathFactory pathFactory;

    private FoxPath currentPath;

    @Inject
    private CommandContextImpl(FoxMainIndex mainIndex, FoxPathFactory pathFactory) {
        this.mainIndex = mainIndex;
        this.pathFactory = pathFactory;
        this.resetPath();
    }

    @Override
    public FoxPath getCurrentPath() {
        return this.currentPath;
    }

    @Override
    public FoxPath resetPath() {
        this.currentPath = pathFactory.from(this.mainIndex.getDefaultObjectIndex().getIndexPath());
        return this.currentPath;
    }

    @Override
    public FoxPath changePath(FoxPath foxPath) throws FoxCommandException {
        FoxPathExt foxPathExt = ((FoxPathExt) foxPath);
        if (foxPathExt.getMode() == FoxPathExt.Mode.HOME) {
            resetPath();
            foxPathExt = foxPathExt.asMode(FoxPathExt.Mode.RELATIVE);
        }
        FoxPath newPath = this.currentPath.resolve(foxPathExt);
        getNamespaceDirect(newPath);
        this.currentPath = newPath;
        return this.currentPath;
    }

    @Override
    public Namespace getNamespace(@Nullable FoxPath path) throws FoxCommandException {
        FoxPath fullPath = this.currentPath.resolve(path);
        return getNamespaceDirect(fullPath);
    }

    @Override
    public Namespace getNamespaceDirect(@Nonnull FoxPath path) throws FoxCommandException {
        FoxPathExt pathExt = ((FoxPathExt) path);
        IndexPathSection indexSection = pathExt.getIndexSection().orElse(null);
        FoxObjectIndex index;
        Namespace namespace;
        if (indexSection == null) {
            index = this.mainIndex.getDefaultObjectIndex();
            namespace = index.getDefaultNamespace();
        } else {
            index = this.mainIndex.getObjectIndex(indexSection.getIndex()).orElse(null);
            if (index == null)
                throw new FoxCommandException("No index with name \"" + indexSection.getIndex() + "\"!");
            namespace = index.getNamespace(indexSection.getNamespacePath()).orElse(null);
            if (namespace == null)
                throw new FoxCommandException("No index namespace with path \"" + indexSection.getNamespacePath() + "\"!");
        }
        return namespace;
    }

    @Override
    public IndexReference getObjectFromIndex(@Nullable FoxPath path) throws FoxCommandException {
        FoxPath fullPath = this.currentPath.resolve(path);
        return getObjectFromIndexDirect(fullPath);
    }

    @Override
    public IndexReference getObjectFromIndexDirect(@Nonnull FoxPath path) throws FoxCommandException {
        FoxPathExt fullPathExt = (FoxPathExt) path;

        Namespace namespace = getNamespaceDirect(fullPathExt);

        ObjectPathSection objectPathComp = fullPathExt.getObjectSection().orElse(null);
        if (objectPathComp == null) throw new FoxCommandException("Must specify an object path!");

        IndexReference reference = namespace.getObjectReference(objectPathComp).orElse(null);
        if (reference == null) throw new FoxCommandException("No object at path \"" + objectPathComp + "\"!");

        return reference;
    }
}
