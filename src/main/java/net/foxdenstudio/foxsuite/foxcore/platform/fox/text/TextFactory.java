package net.foxdenstudio.foxsuite.foxcore.platform.fox.text;

import net.foxdenstudio.foxsuite.foxcore.platform.text.Text;

public interface TextFactory {

    Text of(String text);

    Text of(Object... objects);

    Text.Builder builder();

}
