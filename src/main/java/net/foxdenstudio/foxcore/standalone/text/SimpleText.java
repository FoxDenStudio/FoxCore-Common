package net.foxdenstudio.foxcore.standalone.text;

import net.foxdenstudio.foxcore.platform.text.Text;
import net.foxdenstudio.foxcore.platform.text.format.TextColor;
import net.foxdenstudio.foxcore.platform.text.format.TextColors;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.util.ArrayList;
import java.util.List;

public class SimpleText implements Text {

    private final TextColors textColors;

    private final List<Section> sectionList;
    private String plain = null;

    public SimpleText(TextColors textColors, Object... objects) {
        this.textColors = textColors;
        this.sectionList = new ArrayList<>();
        Section cur = new Section();
        boolean added = false;
        for (Object obj : objects) {
            if (obj instanceof TextColor) {
                cur = new Section();
                cur.color = (TextColor) obj;
                added = false;
            } else {
                cur.text += obj.toString();
                if (!added) {
                    sectionList.add(cur);
                    added = true;
                }
            }
        }
    }

    @Override
    public String toPlain() {
        if (this.plain == null) {
            StringBuilder builder = new StringBuilder();
            for (Section sec : this.sectionList) {
                builder.append(sec.text);
            }
            this.plain = builder.toString();
        }
        return this.plain;
    }

    public String toANSI() {
        AttributedStringBuilder builder = new AttributedStringBuilder();
        for (Section sec : this.sectionList) {
            TextColor color = sec.color;
            if (color instanceof SimpleTextColor) {
                builder.style(((SimpleTextColor) color).getJLineStyle());
            } else {
                builder.style(AttributedStyle.DEFAULT);
            }
            builder.append(sec.text);
        }
        return builder.toAnsi();
    }

    private static class Section {
        public TextColor color;
        public String text = "";
    }
}
