package net.foxdenstudio.foxcore.standalone.command;

import net.foxdenstudio.foxcore.api.command.FoxCommandManager;
import net.foxdenstudio.foxcore.api.command.result.ResultFactory;
import net.foxdenstudio.foxcore.platform.fox.text.TextFactory;
import net.foxdenstudio.foxcore.impl.command.CommandDispatcherImpl;

import javax.inject.Inject;

public class StandaloneCommandManager extends CommandDispatcherImpl implements FoxCommandManager {

    @Inject
    private StandaloneCommandManager(TextFactory textFactory, ResultFactory resultFactory) {
        super(textFactory, resultFactory);
    }
}
