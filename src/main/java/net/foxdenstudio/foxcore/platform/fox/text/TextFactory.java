package net.foxdenstudio.foxcore.platform.fox.text;

import net.foxdenstudio.foxcore.platform.text.Text;

public interface TextFactory {

    Text getText(String text);

    Text getText(Object... objects);

}
