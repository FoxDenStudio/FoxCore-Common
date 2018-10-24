package net.foxdenstudio.foxcore.standalone.command.source;

import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.platform.text.Text;
import org.slf4j.Logger;

public class PlainConsoleTextPrinter implements ConsoleTextPrinter {

    @FoxLogger("chat")
    Logger logger;

    @Override
    public void printText(Text text) {
        logger.info(text.toPlain());
    }
}
