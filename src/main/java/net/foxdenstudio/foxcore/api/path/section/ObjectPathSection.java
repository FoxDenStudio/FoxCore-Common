package net.foxdenstudio.foxcore.api.path.section;

import com.google.common.base.Preconditions;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public final class ObjectPathSection implements FoxPathSection {

    private final StandardPathComponent path;

    public ObjectPathSection(@Nonnull StandardPathComponent path) {
        Preconditions.checkNotNull(path);
        this.path = path;
    }

    public StandardPathComponent getPathComponent(){
        return this.path;
    }

    public static ObjectPathSection of(@Nonnull String first, String... next) {
        return new ObjectPathSection(StandardPathComponent.of(first, next));
    }

    public static ObjectPathSection from(@Nonnull List<String> elements) {
        return new ObjectPathSection(StandardPathComponent.from(elements));
    }

    public static ObjectPathSection from(@Nonnull StandardPathComponent path){
        return new ObjectPathSection(path);
    }

    public int numParts() {
        return path.size();
    }

    public List<String> getElements() {
        return path.elements();
    }

    @Nonnull
    public String getPart(int index) {
        return path.get(index);
    }

    @Nonnull
    public StandardPathComponent resolve(StandardPathComponent component) {
        return path.resolve(component);
    }

    @Override
    public String toString() {
        return path.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectPathSection that = (ObjectPathSection) o;
        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
