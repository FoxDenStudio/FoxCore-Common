package net.foxdenstudio.foxcore.api.command;

import net.foxdenstudio.foxcore.api.command.context.FoxCommandContextManager;
import net.foxdenstudio.foxcore.api.command.result.ResultFactory;
import net.foxdenstudio.foxcore.platform.fox.text.TextFactory;
import net.foxdenstudio.foxcore.platform.text.format.TextColors;

import javax.inject.Inject;

public abstract class FoxCommandBase implements FoxCommand {

    @Inject
    protected TextFactory tf;

    @Inject
    protected TextColors tc;

    @Inject
    protected ResultFactory resultFactory;

    @Inject
    protected FoxCommandContextManager commandContextManager;

    protected FoxCommandBase() {
    }
}
