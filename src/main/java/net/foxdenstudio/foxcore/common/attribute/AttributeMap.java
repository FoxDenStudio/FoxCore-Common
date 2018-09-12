package net.foxdenstudio.foxcore.common.attribute;

import java.util.HashMap;

public class AttributeMap extends HashMap<Class<? extends FoxAttribute<?>>, FoxAttribute<?>> {

    @SuppressWarnings("unchecked")
    public <T> FoxAttribute<T> get(Class<T> key) {
        return (FoxAttribute<T>) get((Object) key);
    }

    @Override
    public FoxAttribute<?> put(Class<? extends FoxAttribute<?>> key, FoxAttribute<?> value) {
        if (!value.getClass().equals(key)) throw new IllegalArgumentException(
                "The key (" + key.getCanonicalName() + ") does not match value type (" + value.getClass() + ")");
        return super.put(key, value);
    }
}
