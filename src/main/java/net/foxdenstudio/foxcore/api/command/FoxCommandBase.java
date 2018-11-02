package net.foxdenstudio.foxcore.api.command;

import net.foxdenstudio.foxcore.api.command.result.ResultFactory;
import net.foxdenstudio.foxcore.platform.fox.text.TextFactory;

import javax.inject.Inject;

public abstract class FoxCommandBase implements FoxCommand {

    protected final TextFactory textFactory;
    protected final ResultFactory resultFactory;

    @Inject
    public FoxCommandBase(TextFactory textFactory, ResultFactory resultFactory) {
        this.textFactory = textFactory;
        this.resultFactory = resultFactory;
    }
}
