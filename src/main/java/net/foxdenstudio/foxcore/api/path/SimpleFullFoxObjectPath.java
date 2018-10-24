package net.foxdenstudio.foxcore.api.path;

import com.google.common.collect.ImmutableList;

import java.util.Collection;

public class SimpleFullFoxObjectPath implements FullFoxObjectPath {

    private final FoxIndexPath indexPath;
    private final FoxObjectPath objectPath;
    private final Collection<FoxLinkPath> linkPaths;

    private SimpleFullFoxObjectPath(FoxIndexPath indexPath,
                                    FoxObjectPath objectPath,
                                    Collection<FoxLinkPath> linkPaths) {
        this.indexPath = indexPath;
        this.objectPath = objectPath;
        this.linkPaths = linkPaths;
    }

    public static SimpleFullFoxObjectPath of(FoxIndexPath indexPath,
                                             FoxObjectPath objectPath,
                                             FoxLinkPath... linkPaths) {
        return new SimpleFullFoxObjectPath(indexPath, objectPath,
                linkPaths == null ? ImmutableList.of() : ImmutableList.copyOf(linkPaths));
    }

    public static SimpleFullFoxObjectPath of(FoxIndexPath indexPath,
                                             FoxObjectPath objectPath,
                                             Iterable<FoxLinkPath> linkPaths) {
        return new SimpleFullFoxObjectPath(indexPath, objectPath,
                linkPaths == null ? ImmutableList.of() : ImmutableList.copyOf(linkPaths));
    }

    @Override
    public FoxIndexPath getIndexPath() {
        return indexPath;
    }

    @Override
    public FoxObjectPath getObjectPath() {
        return objectPath;
    }

    @Override
    public Collection<FoxLinkPath> getLinkPaths() {
        return linkPaths;
    }
}
