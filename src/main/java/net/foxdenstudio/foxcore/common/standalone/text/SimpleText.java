package net.foxdenstudio.foxcore.common.standalone.text;

import net.foxdenstudio.foxcore.common.platform.text.Text;

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
