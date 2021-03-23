package net.foxdenstudio.foxcore.standalone;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.jul.LevelChangePropagator;
import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import net.foxdenstudio.foxcore.FoxCore;
import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.standalone.guice.module.FoxCoreStandaloneModule;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public final class FoxStart {

    private final FoxCore foxCore;

    @FoxLogger("standalone.start")
    private Logger logger;

    @Inject
    public FoxStart(final FoxCore foxCore) {
        this.foxCore = foxCore;
    }

    public final void start(final String[] args) {

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        final LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.addListener(new LevelChangePropagator());

        //Logger logger = (Logger) LoggerFactory.getLogger("org.jline");
        //logger.setLevel(Level.ALL);

        // Sandbox
        {

        }

        foxCore.awoo();
        foxCore.configureCommands();
        foxCore.registerCommands();
        foxCore.setupStaticContent();
        foxCore.loadWorldData();
        foxCore.loadIndexObjects();

        try {
            final Terminal terminal = TerminalBuilder.builder()
                    .system(true)
                    .nativeSignals(true)
                    .signalHandler(Terminal.SignalHandler.SIG_IGN)
                    .build();

            final LineReader lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .build();

            final String prompt = new AttributedStringBuilder()
                    .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED | AttributedStyle.BRIGHT))
                    .append("> ")
                    .toAnsi();

            while (true) {
                try {
                    final String line = lineReader.readLine(prompt);
                    Stopwatch stopwatch = Stopwatch.createStarted();

                    if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("stop")) break;
                    if (line.isEmpty()) continue;
                    try {

                        foxCore.getCommandManager().process(foxCore.getConsoleSource(), line);
                        logger.info("Operation took {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (UserInterruptException | EndOfFileException e) {
                    e.printStackTrace();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final List<Module> modules = new ArrayList<>();
        modules.add(new FoxCoreStandaloneModule());

        final Injector injector = Guice.createInjector(modules);

        final FoxStart foxStart = injector.getInstance(FoxStart.class);
        foxStart.start(args);
    }

}
