package net.foxdenstudio.foxcore.content.command;

import com.google.inject.Inject;
import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.text.format.TextColors;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;

public class CommandStringChar extends FoxStandardCommandBase {

    private final TextColors textColors;

    @Inject
    private CommandStringChar(TextColors textColors) {
        this.textColors = textColors;
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {

        arguments = arguments.trim();

        if (arguments.isEmpty()) {
            String s = "\u2588";
            int cp = Character.codePointAt(s,0);
            source.sendMessage(tf.of(s));
            System.out.println(s);
            System.out.println(s.charAt(0));
            System.out.println(cp);
            System.out.println(Character.getName(cp));
        } else {

            byte[] bytes = arguments.getBytes(StandardCharsets.UTF_8);

            StringBuilder sb = new StringBuilder();
            sb.append("String: \"").append(arguments).append("\" - ")
                    .append(arguments.length()).append(" codeunit(s) - ")
                    .append(Character.codePointCount(arguments, 0, arguments.length())).append(" codepoint(s) - ")
                    .append(bytes.length).append(" byte(s) -");

            for (byte b : bytes) {
                sb.append(' ').append(String.format("%x", b));
            }

            sb.append('\n');

            for (char c : arguments.toCharArray()) {
                sb.append('\'').append(c).append("': ").append(Character.getName(c)).append(" - U+").append(String.format("%x", (short) c)).append('\n');
            }

            source.sendMessage(tf.of(sb.toString()));
        }
        return this.resultFactory.empty();
    }
}
