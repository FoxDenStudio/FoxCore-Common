package net.foxdenstudio.foxcore.api.exception;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import net.foxdenstudio.foxcore.platform.fox.text.TextFactory;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.text.Text;

import javax.inject.Inject;
import java.util.Optional;

public class FoxException extends Exception {

    @Inject
    protected TextFactory textFactory;

    @Inject
    protected FoxException() {
        super();
    }

    @AssistedInject
    protected FoxException(@Assisted String message) {
        super(message);
    }

    @AssistedInject
    protected FoxException(@Assisted String message, @Assisted Throwable cause) {
        super(message, cause);
    }

    @AssistedInject
    protected FoxException(@Assisted Throwable cause) {
        super(cause);
    }

    protected TextFactory getTextFactory(){
        return this.textFactory;
    }

    Optional<Text> getUserMessage(CommandSource source) {
        return Optional.of(this.getTextFactory().getText(getMessage()));
    }
}
