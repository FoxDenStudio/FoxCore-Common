package net.foxdenstudio.foxcore.api.command;

import net.foxdenstudio.foxcore.api.command.result.ResultFactory;
import net.foxdenstudio.foxcore.platform.fox.text.TextFactory;

import javax.inject.Inject;

public abstract class FoxCommandBase implements FoxCommand {

    @Inject
    protected TextFactory textFactory;

    @Inject
    protected ResultFactory resultFactory;

    @Inject
    public FoxCommandBase() {
    }
}
