package net.foxdenstudio.foxcore.standalone.command.source;

import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.platform.text.Text;
import net.foxdenstudio.foxcore.standalone.text.SimpleText;
import org.slf4j.Logger;

public class SimpleConsoleTextPrinter implements ConsoleTextPrinter {

    @FoxLogger("chat")
    Logger logger;

    @Override
    public void printText(Text text) {
        if (text instanceof SimpleText) {
            logger.info(((SimpleText) text).toAttributedString().toAnsi());
        } else {
            logger.info(text.toPlain());
        }
    }
}
