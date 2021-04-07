package net.foxdenstudio.foxsuite.foxcore.standalone.command.source;

import net.foxdenstudio.foxsuite.foxcore.platform.command.source.ConsoleSource;
import net.foxdenstudio.foxsuite.foxcore.platform.text.Text;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SimpleConsoleSource implements ConsoleSource {

    private final ConsoleTextPrinter printer;

    @Inject
    public SimpleConsoleSource(ConsoleTextPrinter printer) {
        this.printer = printer;
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public void sendMessage(Text message) {
        printer.printText(message);
    }

    @Override
    public String getName() {
        return "Console";
    }
}
