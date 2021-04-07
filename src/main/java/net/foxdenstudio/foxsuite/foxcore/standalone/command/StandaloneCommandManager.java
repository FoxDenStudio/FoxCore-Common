package net.foxdenstudio.foxsuite.foxcore.standalone.command;

import net.foxdenstudio.foxsuite.foxcore.impl.command.CommandDispatcherImpl;
import net.foxdenstudio.foxsuite.foxcore.platform.command.PlatformCommandManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StandaloneCommandManager extends CommandDispatcherImpl implements PlatformCommandManager {

    @Inject
    private StandaloneCommandManager() {
    }
}
