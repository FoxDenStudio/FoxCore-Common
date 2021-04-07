package net.foxdenstudio.foxsuite.foxcore.guice.module;

import com.google.inject.AbstractModule;
import net.foxdenstudio.foxsuite.foxcore.api.util.NameChecker;
import net.foxdenstudio.foxsuite.foxcore.impl.util.NameCheckerImpl;

public class UtilModule extends AbstractModule {

    public static final UtilModule INSTANCE = new UtilModule();

    private UtilModule() {
    }

    protected void configure() {
        bind(NameChecker.class).to(NameCheckerImpl.class);
    }
}
