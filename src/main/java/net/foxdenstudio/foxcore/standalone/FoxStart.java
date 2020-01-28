package net.foxdenstudio.foxcore.standalone;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.jul.LevelChangePropagator;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import net.foxdenstudio.foxcore.FoxCore;
import net.foxdenstudio.foxcore.standalone.guice.module.FoxCoreStandaloneModule;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.impl.DumbTerminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoxStart {

    private final FoxCore foxCore;

    @Inject
    public FoxStart(FoxCore foxCore) {
        this.foxCore = foxCore;
    }

    public void start(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.addListener(new LevelChangePropagator());

        Logger logger = (Logger) LoggerFactory.getLogger("org.jline");
        //logger.setLevel(Level.ALL);

        foxCore.awoo();
        foxCore.configureCommands();
        foxCore.registerCommands();
        foxCore.setupStaticContent();

        try {
            Terminal terminal = TerminalBuilder.builder()
                    .system(true)
                    .nativeSignals(true)
                    .signalHandler(Terminal.SignalHandler.SIG_IGN)
                    .build();

            LineReader lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .build();

            while (true) {
                try {
                    String line = lineReader.readLine(
                            new AttributedStringBuilder()
                                    .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED | AttributedStyle.BRIGHT))
                                    .append("> ")
                                    .toAnsi());
                    if (line.equalsIgnoreCase("exit")) break;
                    if (line.isEmpty()) continue;
                    try {
                        foxCore.getCommandManager().process(foxCore.getConsoleSource(), line);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (UserInterruptException e){
                    e.printStackTrace();
                    break;
                } catch (EndOfFileException e){
                    e.printStackTrace();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        List<Module> modules = new ArrayList<>();
        modules.add(new FoxCoreStandaloneModule());
        Injector injector = Guice.createInjector(modules);

        FoxStart foxStart = injector.getInstance(FoxStart.class);
        foxStart.start(args);
    }


}
