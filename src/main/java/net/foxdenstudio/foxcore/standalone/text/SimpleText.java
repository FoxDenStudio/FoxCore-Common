package net.foxdenstudio.foxcore.standalone.text;

import net.foxdenstudio.foxcore.platform.text.Text;

public class SimpleText implements Text {

    private final String text;

    public SimpleText(String text) {
        this.text = text;
    }

    @Override
    public String toPlain() {
        return text;
    }
}
