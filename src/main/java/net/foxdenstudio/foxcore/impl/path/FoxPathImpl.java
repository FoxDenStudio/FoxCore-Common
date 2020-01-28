package net.foxdenstudio.foxcore.impl.path;

import com.google.common.collect.ImmutableList;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.section.FoxPathSection;
import net.foxdenstudio.foxcore.api.path.resolve.ResolveConfig;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class FoxPathImpl implements FoxPath {

    @Nonnull
    protected List<FoxPathSection> components;

    public FoxPathImpl (List<FoxPathSection> components){
        this.components = components.stream().filter(Objects::nonNull).collect(ImmutableList.toImmutableList());
    }

    @Nonnull
    @Override
    public FoxPath resolve(FoxPath path, ResolveConfig config) {
        return path == null ? this : path;
    }

    @Nonnull
    @Override
    public List<FoxPathSection> getSections() {
        return this.components;
    }

    @Override
    public boolean isEmpty() {
        return this.components.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoxPathImpl foxPath = (FoxPathImpl) o;
        return components.equals(foxPath.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(components);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(Iterator<FoxPathSection> it = this.components.iterator(); it.hasNext();){
            FoxPathSection component = it.next();
            builder.append(component);
            if(it.hasNext())
                builder.append(':');
        }
        return builder.toString();
    }
}
