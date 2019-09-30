package net.foxdenstudio.foxcore.standalone.text;

import net.foxdenstudio.foxcore.platform.text.format.TextColor;
import org.jline.utils.AttributedStyle;

public class SimpleTextColor implements TextColor {

    private final String id;
    private final String name;
    private final int color;
    private final boolean reset;

    private SimpleTextColor(String id, String name, int color, boolean reset) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.reset = reset;
    }

    public SimpleTextColor(String id, String name, int color) {
        this(id, name, color, false);
    }

    public SimpleTextColor(String id, String name, boolean reset) {
        this(id, name, -1, reset);
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public AttributedStyle getJLineStyle() {
        AttributedStyle style = AttributedStyle.DEFAULT;
        if (color < 0) {
            if (reset) style = style.foregroundOff();
            else style = style.foregroundDefault();
        } else {
             style = style.foreground(color);
        }
        return style;
    }

    public int getANSIColor() {
        return color;
    }
}
