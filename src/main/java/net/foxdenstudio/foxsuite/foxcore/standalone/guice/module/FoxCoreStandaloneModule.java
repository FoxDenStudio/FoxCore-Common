package net.foxdenstudio.foxsuite.foxcore.standalone.guice.module;

import com.google.inject.AbstractModule;
import net.foxdenstudio.foxsuite.foxcore.api.world.FoxWorldManager;
import net.foxdenstudio.foxsuite.foxcore.guice.module.FoxCoreModule;
import net.foxdenstudio.foxsuite.foxcore.platform.command.PlatformCommandManager;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.ConsoleSource;
import net.foxdenstudio.foxsuite.foxcore.standalone.command.StandaloneCommandManager;
import net.foxdenstudio.foxsuite.foxcore.standalone.command.source.ConsoleTextPrinter;
import net.foxdenstudio.foxsuite.foxcore.standalone.command.source.SimpleConsoleTextPrinter;
import net.foxdenstudio.foxsuite.foxcore.standalone.command.source.SimpleConsoleSource;
import net.foxdenstudio.foxsuite.foxcore.standalone.fox.text.SimpleTextFactory;
import net.foxdenstudio.foxsuite.foxcore.platform.fox.text.TextFactory;
import net.foxdenstudio.foxsuite.foxcore.standalone.world.StandaloneWorldManager;

public class FoxCoreStandaloneModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FoxCoreModule());
        install(new StandaloneTextStyleModule());
        bind(PlatformCommandManager.class).to(StandaloneCommandManager.class);
        bind(ConsoleSource.class).to(SimpleConsoleSource.class);
        bind(ConsoleTextPrinter.class).to(SimpleConsoleTextPrinter.class);
        bind(TextFactory.class).to(SimpleTextFactory.class);
        bind(FoxWorldManager.class).to(StandaloneWorldManager.class);
        //bind(ResultFactory.class).to(ResultFactoryImpl.class);

    }

}
