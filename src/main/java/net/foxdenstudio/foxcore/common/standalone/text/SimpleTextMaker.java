package net.foxdenstudio.foxcore.common.standalone.text;

import net.foxdenstudio.foxcore.common.platform.text.Text;
import net.foxdenstudio.foxcore.common.text.TextMaker;

import javax.inject.Singleton;

@Singleton
public class SimpleTextMaker implements TextMaker {

    @Override
    public Text getText(String text) {
        return new SimpleText(text);
    }

}
