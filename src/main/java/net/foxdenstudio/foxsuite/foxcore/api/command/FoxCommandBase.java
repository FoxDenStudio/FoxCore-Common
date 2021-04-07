package net.foxdenstudio.foxsuite.foxcore.api.command;

import net.foxdenstudio.foxsuite.foxcore.api.command.context.FoxCommandContextManager;
import net.foxdenstudio.foxsuite.foxcore.api.command.result.ResultFactory;
import net.foxdenstudio.foxsuite.foxcore.platform.fox.text.TextFactory;
import net.foxdenstudio.foxsuite.foxcore.platform.text.format.TextColors;

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
