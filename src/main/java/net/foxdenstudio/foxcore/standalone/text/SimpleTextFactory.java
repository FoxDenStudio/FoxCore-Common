package net.foxdenstudio.foxcore.standalone.text;

import net.foxdenstudio.foxcore.platform.text.Text;
import net.foxdenstudio.foxcore.api.text.TextFactory;

import javax.inject.Singleton;

@Singleton
public class SimpleTextFactory implements TextFactory {

    @Override
    public Text getText(String text) {
        return new SimpleText(text);
    }

}
