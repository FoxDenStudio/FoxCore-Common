package net.foxdenstudio.foxsuite.foxcore.platform.text.format;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public final class TextColors {

    public final TextColor BLACK;
    public final TextColor DARK_BLUE;
    public final TextColor DARK_GREEN;
    public final TextColor DARK_AQUA;
    public final TextColor DARK_RED;
    public final TextColor DARK_PURPLE;
    public final TextColor GOLD;
    public final TextColor GRAY;
    public final TextColor DARK_GRAY;
    public final TextColor BLUE;
    public final TextColor GREEN;
    public final TextColor AQUA;
    public final TextColor RED;
    public final TextColor LIGHT_PURPLE;
    public final TextColor YELLOW;
    public final TextColor WHITE;


    public final TextColor RESET;
    public final TextColor NONE;

    @Inject
    private TextColors(@Named("black") TextColor black,
                       @Named("dark_blue") TextColor dark_blue,
                       @Named("dark_green") TextColor dark_green,
                       @Named("dark_aqua") TextColor dark_aqua,
                       @Named("dark_red") TextColor dark_red,
                       @Named("dark_purple") TextColor dark_purple,
                       @Named("gold") TextColor gold,
                       @Named("gray") TextColor gray,
                       @Named("dark_gray") TextColor dark_gray,
                       @Named("blue") TextColor blue,
                       @Named("green") TextColor green,
                       @Named("aqua") TextColor aqua,
                       @Named("red") TextColor red,
                       @Named("light_purple") TextColor light_purple,
                       @Named("yellow") TextColor yellow,
                       @Named("white") TextColor white,
                       @Named("reset") TextColor reset,
                       @Named("none") TextColor none) {
        BLACK = black;
        DARK_BLUE = dark_blue;
        DARK_GREEN = dark_green;
        DARK_AQUA = dark_aqua;
        DARK_RED = dark_red;
        DARK_PURPLE = dark_purple;
        GOLD = gold;
        GRAY = gray;
        DARK_GRAY = dark_gray;
        BLUE = blue;
        GREEN = green;
        AQUA = aqua;
        RED = red;
        LIGHT_PURPLE = light_purple;
        YELLOW = yellow;
        WHITE = white;
        RESET = reset;
        NONE = none;
    }
}
