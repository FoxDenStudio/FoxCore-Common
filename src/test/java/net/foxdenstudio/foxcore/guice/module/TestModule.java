package net.foxdenstudio.foxcore.guice.module;

import com.google.inject.AbstractModule;
import net.foxdenstudio.foxcore.platform.fox.text.TextFactory;
import net.foxdenstudio.foxcore.standalone.fox.text.SimpleTextFactory;

public class TestModule extends AbstractModule {

    public static final TestModule INSTANCE = new TestModule();

    private TestModule() {
    }

    protected void configure() {
        bind(TextFactory.class).to(SimpleTextFactory.class);
    }

}
