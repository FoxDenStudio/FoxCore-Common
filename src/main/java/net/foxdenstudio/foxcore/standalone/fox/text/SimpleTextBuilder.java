package net.foxdenstudio.foxcore.standalone.fox.text;

import net.foxdenstudio.foxcore.platform.text.Text;
import net.foxdenstudio.foxcore.standalone.text.SimpleText;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class SimpleTextBuilder implements Text.Builder {

    private List<Text> parts = new ArrayList<>();

    @Inject
    private SimpleTextBuilder(){}


    @Override
    public Text.Builder append(Text obj) {
        parts.add(obj);
        return this;
    }

    @Override
    public Text build() {
        return new SimpleText(parts);
    }
}
