package net.foxdenstudio.foxcore.api.path.section;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class LinkPathSection implements FoxPathSection {

    private final List<StandardPathComponent> links;

    public static LinkPathSection of(@Nonnull StandardPathComponent first, StandardPathComponent... next) {
        Preconditions.checkNotNull(first);
        ImmutableList.Builder<StandardPathComponent> builder = ImmutableList.builder();
        builder.add(first);

        if (next != null) {
            for (StandardPathComponent comp : next) {
                if (comp != null) {
                    builder.add(comp);
                }
            }
        }
        return new LinkPathSection(builder.build());
    }

    public static LinkPathSection of(@Nonnull LinkPathSection linkPathSection, StandardPathComponent... next){
        return of(linkPathSection.links, next);
    }

    public static LinkPathSection of(@Nonnull List<StandardPathComponent> components, StandardPathComponent... next) {
        ImmutableList.Builder<StandardPathComponent> builder = ImmutableList.builder();
        builder.addAll(components);

        components.stream().filter(Objects::nonNull).forEach(builder::add);
        if (next != null) {
            Arrays.stream(next).filter(Objects::nonNull).forEach(builder::add);
        }

        List<StandardPathComponent> list = builder.build();
        Preconditions.checkArgument(!list.isEmpty(), "Must supply at least one non-null element!");
        return new LinkPathSection(list);
    }

    private LinkPathSection(List<StandardPathComponent> links) {
        this.links = links;
    }

    public List<StandardPathComponent> getLinkComponents() {
        return links;
    }

    @Override
    public String toString() {
        Iterator<StandardPathComponent> it = links.iterator();
        StringBuilder builder = new StringBuilder();
        while (it.hasNext()) {
            StandardPathComponent comp = it.next();
            builder.append(comp);
            if (it.hasNext()) {
                builder.append(':');
            }
        }
        return builder.toString();
    }

}
