package net.foxdenstudio.foxcore.standalone.command;

import net.foxdenstudio.foxcore.impl.command.CommandDispatcherImpl;
import net.foxdenstudio.foxcore.platform.command.PlatformCommandManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StandaloneCommandManager extends CommandDispatcherImpl implements PlatformCommandManager {

    @Inject
    private StandaloneCommandManager() {
    }
}
