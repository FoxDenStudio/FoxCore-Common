package net.foxdenstudio.foxsuite.foxcore.standalone.fox.text;

import net.foxdenstudio.foxsuite.foxcore.platform.text.Text;
import net.foxdenstudio.foxsuite.foxcore.standalone.text.SimpleText;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleTextBuilder implements Text.Builder {

    private List<Text> parts = new ArrayList<>();

    @Inject
    private SimpleTextBuilder(){}


    @Override
    public Text.Builder append(Text... children) {
        parts.addAll(Arrays.asList(children));
        return this;
    }

    @Override
    public Text build() {
        return new SimpleText(parts);
    }
}
