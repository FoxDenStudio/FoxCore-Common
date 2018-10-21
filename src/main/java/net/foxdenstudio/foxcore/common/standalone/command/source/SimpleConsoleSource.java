package net.foxdenstudio.foxcore.common.standalone.command.source;

import net.foxdenstudio.foxcore.common.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.common.platform.command.source.ConsoleSource;
import net.foxdenstudio.foxcore.common.platform.text.Text;
import org.slf4j.Logger;

import javax.inject.Singleton;

@Singleton
public class SimpleConsoleSource implements ConsoleSource {

    @FoxLogger("chat")
    Logger logger;

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public void sendMessage(Text message) {
        logger.info(message.toPlain());
    }

    @Override
    public String getName() {
        return "Console";
    }
}
