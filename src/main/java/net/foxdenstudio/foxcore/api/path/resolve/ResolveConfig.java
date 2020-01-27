package net.foxdenstudio.foxcore.api.path.resolve;

import java.util.HashMap;
import java.util.Map;

public class ResolveConfig {

    private Map<ResolveOption<?>, Object> options;

    public ResolveConfig() {
        this.options = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(ResolveOption<T> option) {
        T var = (T) options.get(option);
        if (var == null) {
            var = option.getDefaultValue();
            this.options.put(option, var);
        }
        return var;
    }

    public <T> void set(ResolveOption<T> option, T value) {
        this.options.put(option, value);
    }

}
