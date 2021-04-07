package net.foxdenstudio.foxsuite.foxcore.api.path.resolve;

public class ResolveOption<T> {

    private String name;
    private T defaultValue;

    private ResolveOption(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public static <T> ResolveOption<T> of(String name, T defaultValue) {
        return new ResolveOption<>(name, defaultValue);
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public static <O extends ResolveOption<T>, T> Value<O, T> value(O option, T value) {
        return new Value<>(option, value);
    }

    public static class Value<O extends ResolveOption<T>, T> {
        private final O option;
        private final T value;

        public O getOption() {
            return option;
        }

        public T getValue() {
            return value;
        }

        public Value(O option, T value) {
            this.option = option;
            this.value = value;
        }


    }
}
