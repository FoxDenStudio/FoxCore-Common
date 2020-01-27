package net.foxdenstudio.foxcore.api.path.component;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class LinkPathComponent implements FoxPathComponent {

    private final List<StandardPathComponent> links;

    @SuppressWarnings("ConstantConditions")
    public static LinkPathComponent of(@Nonnull StandardPathComponent first, StandardPathComponent... next) {
        boolean added = false;
        ImmutableList.Builder<StandardPathComponent> builder = ImmutableList.builder();
        if (first != null) {
            builder.add(first);
            added = true;
        }
        if (next != null) {
            for (StandardPathComponent comp : next) {
                if (comp != null) {
                    builder.add(comp);
                    added = true;
                }
            }
        }
        Preconditions.checkArgument(added, "Must supply at least one non-null element!");
        return new LinkPathComponent(builder.build());
    }

    public static LinkPathComponent of(List<StandardPathComponent> components) {
        List<StandardPathComponent> list = components.stream().filter(Objects::nonNull).collect(ImmutableList.toImmutableList());
        Preconditions.checkArgument(!list.isEmpty(), "Must supply at least one non-null element!");
        return new LinkPathComponent(list);
    }

    private LinkPathComponent(List<StandardPathComponent> links) {
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
