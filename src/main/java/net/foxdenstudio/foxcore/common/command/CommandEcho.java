package net.foxdenstudio.foxcore.common.command;

import net.foxdenstudio.foxcore.common.platform.command.CommandSource;
import net.foxdenstudio.foxcore.common.text.TextMaker;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class CommandEcho implements FoxCommand {

    private final TextMaker textMaker;

    @Inject
    public CommandEcho(TextMaker textMaker) {
        this.textMaker = textMaker;
    }

    @Override
    public boolean process(@Nonnull CommandSource source, @Nonnull String arguments) {
        String message = source.getName() + " said: "
                + (arguments.isEmpty() ? "(empty)" : arguments);
        source.sendMessage(textMaker.getText(message));
        return true;
    }
}
