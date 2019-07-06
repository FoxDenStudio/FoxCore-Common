package net.foxdenstudio.foxcore.api.command.standard;

import net.foxdenstudio.foxcore.api.command.FoxCommandBase;

import javax.inject.Inject;

public abstract class FoxStandardCommandBase extends FoxCommandBase implements FoxStandardCommand {

    @Inject
    public FoxStandardCommandBase() {
    }
}
