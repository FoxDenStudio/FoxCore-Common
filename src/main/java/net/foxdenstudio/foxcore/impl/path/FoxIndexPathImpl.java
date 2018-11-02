package net.foxdenstudio.foxcore.impl.path;

import net.foxdenstudio.foxcore.api.path.components.FoxIndexPath;
import net.foxdenstudio.foxcore.api.path.components.FoxNamespacePath;

import java.util.Objects;

public class FoxIndexPathImpl implements FoxIndexPath {

    private final String indexType;
    private final FoxNamespacePath namespacePath;

    private transient int hash;
    private transient boolean hashed = false;

    private FoxIndexPathImpl(String indexType, FoxNamespacePath namespacePath) {
        this.indexType = indexType;
        this.namespacePath = namespacePath;
    }

    public static FoxIndexPathImpl of(String indexType, FoxNamespacePath namespacePath) {
        if(indexType.isEmpty() && !namespacePath.isEmpty())
            throw new IllegalArgumentException("namespacePath must be empty if indexType is empty!");
        return new FoxIndexPathImpl(indexType, namespacePath);
    }

    @Override
    public String getIndexType() {
        return this.indexType;
    }

    @Override
    public FoxNamespacePath getNamespacePath() {
        return this.namespacePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoxIndexPathImpl that = (FoxIndexPathImpl) o;
        return indexType.equals(that.indexType) &&
                namespacePath.equals(that.namespacePath);
    }

    @Override
    public int hashCode() {
        if (!hashed) {
            hash = Objects.hash(indexType, namespacePath);
            hashed = true;
        }
        return hash;
    }

    @Override
    public boolean isEmpty() {
        return this.indexType.isEmpty() && this.namespacePath.isEmpty();
    }

    @Override
    public String toString() {
        if(isEmpty()) return "";

        StringBuilder builder = new StringBuilder(this.indexType);

        if(!this.namespacePath.isEmpty()){
            builder.append("/").append(this.namespacePath.toString());
        }

        return builder.toString();
    }
}
