package net.foxdenstudio.foxcore.common.standalone.guice.module;

import com.google.inject.AbstractModule;
import net.foxdenstudio.foxcore.common.command.FoxCommandManager;
import net.foxdenstudio.foxcore.common.guice.module.FoxCoreModule;
import net.foxdenstudio.foxcore.common.platform.command.source.ConsoleSource;
import net.foxdenstudio.foxcore.common.standalone.command.SimpleFoxCommandManager;
import net.foxdenstudio.foxcore.common.standalone.command.source.SimpleConsoleSource;
import net.foxdenstudio.foxcore.common.standalone.text.SimpleTextMaker;
import net.foxdenstudio.foxcore.common.text.TextMaker;

public class FoxCoreStandaloneModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FoxCoreModule());
        bind(FoxCommandManager.class).to(SimpleFoxCommandManager.class);
        bind(ConsoleSource.class).to(SimpleConsoleSource.class);
        bind(TextMaker.class).to(SimpleTextMaker.class);
    }

}
