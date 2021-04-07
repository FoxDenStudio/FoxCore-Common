package net.foxdenstudio.foxsuite.foxcore.api.text;

import net.foxdenstudio.foxsuite.foxcore.platform.text.Text;
import net.foxdenstudio.foxsuite.foxcore.platform.text.TextRepresentable;

public interface FoxTextRepresentable extends TextRepresentable {

    @Override
    Text toText();

    @Override
    default void applyTo(Text.Builder builder) {
        builder.append(toText());
    }
}
