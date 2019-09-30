package net.foxdenstudio.foxcore.standalone.fox.text;

import net.foxdenstudio.foxcore.platform.fox.text.TextFactory;
import net.foxdenstudio.foxcore.platform.text.Text;
import net.foxdenstudio.foxcore.platform.text.format.TextColors;
import net.foxdenstudio.foxcore.standalone.text.SimpleText;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SimpleTextFactory implements TextFactory {

    private final TextColors textColors;

    @Inject
    private SimpleTextFactory(TextColors textColors) {
        this.textColors = textColors;
    }

    @Override
    public Text getText(String text) {
        return new SimpleText(this.textColors, text);
    }

    @Override
    public Text getText(Object... objects) {
        return new SimpleText(this.textColors, objects);
    }

}
