package net.foxdenstudio.foxsuite.foxcore.standalone;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.jul.LevelChangePropagator;
import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import net.foxdenstudio.foxsuite.foxcore.FoxBootstrapper;
import net.foxdenstudio.foxsuite.foxcore.FoxCore;
import net.foxdenstudio.foxsuite.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.ConsoleSource;
import net.foxdenstudio.foxsuite.foxcore.standalone.guice.module.FoxCoreStandaloneModule;
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
import java.util.concurrent.TimeUnit;

public final class FoxStart {

    private static final String FOXGUARD_BOOTSTRAP_CLASS = "net.foxdenstudio.foxsuite.foxguard.FoxGuardBootstrap";

    private final FoxBootstrapper bootstrapper;
    private final Injector injector;

    private FoxCore foxCore;

    @FoxLogger("standalone.start")
    private Logger logger;

    @Inject
    public FoxStart(final FoxBootstrapper bootstrapper, Injector injector) {
        this.bootstrapper = bootstrapper;
        this.injector = injector;
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

        // Try loading FoxGuard manually.

        FoxBootstrapper.Plugin<?> foxguardBootstrapper;
        try {
            foxguardBootstrapper = (FoxBootstrapper.Plugin<?>) this.injector.getInstance(Class.forName(FOXGUARD_BOOTSTRAP_CLASS));
            foxguardBootstrapper.configure(this.bootstrapper);
        } catch (Exception e) {
            logger.warn("Could not load guice bootstrap for plugin FoxGuard", e);
        }


        this.foxCore = bootstrapper.bootstrap();

        foxCore.awoo();
        foxCore.configureCommands();
        foxCore.registerCommands();
        foxCore.setupStaticContent();
        foxCore.loadWorldData();
        foxCore.loadIndexObjects();

        this.bootstrapper.init();

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

            ConsoleSource consoleSource = foxCore.getConsoleSource();
            while (true) {
                Stopwatch stopwatch = Stopwatch.createUnstarted();
                try {
                    final String line = lineReader.readLine(prompt);

                    if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("stop")) break;
                    if (line.isEmpty()) continue;
                    try {
                        stopwatch.start();
                        foxCore.getCommandManager().process(consoleSource, line);
                        stopwatch.stop();
                        logger.info("Operation took {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
                    } catch (Exception e) {
                        logger.warn("Exception executing command line: {}", line, e);
                    }
                    stopwatch.reset();
                } catch (UserInterruptException | EndOfFileException e) {
                    logger.info("Encountered shell interrupt. Exiting...", e);
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("Could not establish terminal communication.", e);
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
