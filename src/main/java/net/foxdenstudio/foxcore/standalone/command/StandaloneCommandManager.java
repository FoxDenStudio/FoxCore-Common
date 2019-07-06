package net.foxdenstudio.foxcore.standalone.command;

import net.foxdenstudio.foxcore.api.command.standard.FoxCommandManager;
import net.foxdenstudio.foxcore.impl.command.CommandDispatcherImpl;

import javax.inject.Inject;

public class StandaloneCommandManager extends CommandDispatcherImpl implements FoxCommandManager {

    @Inject
    private StandaloneCommandManager() {
    }
}
