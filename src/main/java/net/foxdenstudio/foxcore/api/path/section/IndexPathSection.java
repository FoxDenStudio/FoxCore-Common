package net.foxdenstudio.foxcore.api.path.section;

import com.google.common.base.Preconditions;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public final class IndexPathSection implements FoxPathSection {

    private final String index;
    private final StandardPathComponent namespace;

    public IndexPathSection(@Nonnull String index, @Nullable StandardPathComponent namespace) {
        Preconditions.checkNotNull(index);
        Preconditions.checkArgument(!index.isEmpty(), "Index name cannot be empty!");
        this.index = index;
        this.namespace = namespace;
    }

    public String getIndex() {
        return this.index;
    }

    public StandardPathComponent getNamespacePath() {
        return this.namespace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexPathSection that = (IndexPathSection) o;
        return index.equals(that.index) &&
                namespace.equals(that.namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, namespace);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(index);
        if (namespace != null) {
            builder.append('/').append(namespace);
        }
        return builder.toString();
    }
}
