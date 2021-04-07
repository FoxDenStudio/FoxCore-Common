package net.foxdenstudio.foxsuite.foxcore.platform.text;

public interface TextRepresentable extends TextElement {

    Text toText();

    @Override
    default void applyTo(Text.Builder builder) {
        builder.append(toText());
    }
}
