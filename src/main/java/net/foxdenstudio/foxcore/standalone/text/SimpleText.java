package net.foxdenstudio.foxcore.standalone.text;

import net.foxdenstudio.foxcore.platform.text.Text;
import net.foxdenstudio.foxcore.platform.text.format.TextColor;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleText implements Text {

    private final List<Section> sectionList;
    private transient String plain = null;
    private transient AttributedString attributed = null;

    public static SimpleText of(Object... objects){
        return new SimpleText(Arrays.asList(objects));
    }

    public SimpleText(Iterable<?> objects) {
        this.sectionList = new ArrayList<>();
        SimpleSection cur = new SimpleSection();
        boolean added = false;
        for (Object obj : objects) {
            if (obj instanceof Text) {
                this.sectionList.add(new TextSection(((Text) obj)));
                added = false;
            } else if (obj instanceof TextColor) {
                cur = new SimpleSection();
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
                if (sec instanceof SimpleSection) {
                    builder.append(((SimpleSection) sec).text);
                } else if (sec instanceof TextSection) {
                    builder.append(((TextSection) sec).text.toPlain());
                } else {
                    builder.append("<ERROR!>");
                }
            }
            this.plain = builder.toString();
        }
        return this.plain;
    }

    public AttributedString toAttributedString() {
        if (this.attributed == null) {
            AttributedStringBuilder builder = new AttributedStringBuilder();
            for (Section sec : this.sectionList) {
                sec.apply(builder);
            }
            this.attributed = builder.toAttributedString();
        }
        return this.attributed;
    }

    private interface Section {
        void apply(AttributedStringBuilder builder);
    }

    private static class SimpleSection implements Section {
        public TextColor color;
        public String text = "";

        @Override
        public void apply(AttributedStringBuilder builder) {
            if (this.color instanceof SimpleTextColor) {
                builder.style(((SimpleTextColor) this.color).getJLineStyle());
            } else {
                builder.style(AttributedStyle.DEFAULT);
            }
            builder.append(this.text);
        }
    }

    private static class TextSection implements Section {
        public Text text;

        public TextSection(Text text) {
            this.text = text;
        }

        @Override
        public void apply(AttributedStringBuilder builder) {
            AttributedString string;
            if (this.text instanceof SimpleText) {
                string = ((SimpleText) this.text).toAttributedString();
            } else {
                string = new AttributedString(this.text.toPlain(), AttributedStyle.DEFAULT);
            }
            builder.append(string);
        }
    }
}
