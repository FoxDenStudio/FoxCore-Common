package net.foxdenstudio.foxcore.standalone.guice.module;

import com.google.inject.AbstractModule;
import net.foxdenstudio.foxcore.api.command.FoxCommandManager;
import net.foxdenstudio.foxcore.guice.module.FoxCoreModule;
import net.foxdenstudio.foxcore.platform.command.source.ConsoleSource;
import net.foxdenstudio.foxcore.standalone.command.SimpleFoxCommandManager;
import net.foxdenstudio.foxcore.standalone.command.source.ConsoleTextPrinter;
import net.foxdenstudio.foxcore.standalone.command.source.PlainConsoleTextPrinter;
import net.foxdenstudio.foxcore.standalone.command.source.SimpleConsoleSource;
import net.foxdenstudio.foxcore.standalone.text.SimpleTextFactory;
import net.foxdenstudio.foxcore.api.text.TextFactory;

public class FoxCoreStandaloneModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FoxCoreModule());
        bind(FoxCommandManager.class).to(SimpleFoxCommandManager.class);
        bind(ConsoleSource.class).to(SimpleConsoleSource.class);
        bind(ConsoleTextPrinter.class).to(PlainConsoleTextPrinter.class);
        bind(TextFactory.class).to(SimpleTextFactory.class);
    }

}
