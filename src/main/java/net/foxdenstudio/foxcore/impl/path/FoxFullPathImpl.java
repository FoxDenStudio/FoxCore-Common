package net.foxdenstudio.foxcore.impl.path;

import com.google.common.collect.ImmutableList;
import net.foxdenstudio.foxcore.api.path.components.FoxFullPath;
import net.foxdenstudio.foxcore.api.path.components.FoxIndexPath;
import net.foxdenstudio.foxcore.api.path.components.FoxLinkPath;
import net.foxdenstudio.foxcore.api.path.components.FoxObjectPath;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class FoxFullPathImpl implements FoxFullPath {

    private final FoxIndexPath indexPath;
    private final FoxObjectPath objectPath;
    private final List<FoxLinkPath> linkPaths;

    private transient int hash;
    private transient boolean hashed = false;

    private FoxFullPathImpl(FoxIndexPath indexPath,
                            FoxObjectPath objectPath,
                            List<FoxLinkPath> linkPaths) {
        this.indexPath = indexPath;
        this.objectPath = objectPath;
        this.linkPaths = linkPaths;
    }

    public static FoxFullPathImpl of(FoxIndexPath indexPath,
                                     FoxObjectPath objectPath,
                                     FoxLinkPath... linkPaths) {
        return new FoxFullPathImpl(indexPath, objectPath,
                linkPaths == null ? ImmutableList.of() : ImmutableList.copyOf(linkPaths));
    }

    public static FoxFullPathImpl of(FoxIndexPath indexPath,
                                     FoxObjectPath objectPath,
                                     Iterable<FoxLinkPath> linkPaths) {
        return new FoxFullPathImpl(indexPath, objectPath,
                linkPaths == null ? ImmutableList.of() : ImmutableList.copyOf(linkPaths));
    }

    @Override
    public FoxIndexPath getIndexPath() {
        return this.indexPath;
    }

    @Override
    public FoxObjectPath getObjectPath() {
        return this.objectPath;
    }

    @Override
    public List<FoxLinkPath> getLinkPaths() {
        return this.linkPaths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoxFullPathImpl that = (FoxFullPathImpl) o;
        return indexPath.equals(that.indexPath) &&
                objectPath.equals(that.objectPath) &&
                linkPaths.equals(that.linkPaths);
    }

    @Override
    public int hashCode() {
        if (!hashed) {
            hash =  Objects.hash(indexPath, objectPath, linkPaths);
            hashed = true;
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if(!this.indexPath.isEmpty()){
            builder.append("@").append(indexPath.toString()).append(":");
        }
        builder.append(objectPath.toString());

        for(FoxLinkPath path : this.linkPaths){
            builder.append("/").append(path.toString());
        }

        return builder.toString();
    }
}
