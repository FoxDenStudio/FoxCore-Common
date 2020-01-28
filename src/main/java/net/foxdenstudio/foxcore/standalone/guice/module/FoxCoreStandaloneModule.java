package net.foxdenstudio.foxcore.standalone.guice.module;

import com.google.inject.AbstractModule;
import net.foxdenstudio.foxcore.guice.module.FoxCoreModule;
import net.foxdenstudio.foxcore.platform.command.PlatformCommandManager;
import net.foxdenstudio.foxcore.platform.command.source.ConsoleSource;
import net.foxdenstudio.foxcore.standalone.command.StandaloneCommandManager;
import net.foxdenstudio.foxcore.standalone.command.source.ConsoleTextPrinter;
import net.foxdenstudio.foxcore.standalone.command.source.SimpleConsoleTextPrinter;
import net.foxdenstudio.foxcore.standalone.command.source.SimpleConsoleSource;
import net.foxdenstudio.foxcore.standalone.fox.text.SimpleTextFactory;
import net.foxdenstudio.foxcore.platform.fox.text.TextFactory;

public class FoxCoreStandaloneModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FoxCoreModule());
        install(new StandaloneTextStyleModule());
        bind(PlatformCommandManager.class).to(StandaloneCommandManager.class);
        bind(ConsoleSource.class).to(SimpleConsoleSource.class);
        bind(ConsoleTextPrinter.class).to(SimpleConsoleTextPrinter.class);
        bind(TextFactory.class).to(SimpleTextFactory.class);
        //bind(ResultFactory.class).to(ResultFactoryImpl.class);

    }

}
