package net.foxdenstudio.foxsuite.foxcore.standalone.command.source;

import net.foxdenstudio.foxsuite.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxsuite.foxcore.platform.text.Text;
import net.foxdenstudio.foxsuite.foxcore.standalone.text.SimpleText;
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
