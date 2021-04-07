package net.foxdenstudio.foxsuite.foxcore.content.command;

import net.foxdenstudio.foxsuite.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxsuite.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxsuite.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxsuite.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxsuite.foxcore.standalone.guice.module.FoxCoreStandaloneModule;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class DebugSystem extends FoxStandardCommandBase {


    private boolean enabled = false;
    private int armCount = 0;

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        String[] parts = arguments.split(" +");
        if (parts.length == 0) return resultFactory.empty();

        switch (parts[0]) {
            case "on":
                enabled = true;
                armCount = 0;
                source.sendMessage(tf.of("Enabled printing"));
                break;
            case "off":
                enabled = false;
                armCount = 0;
                source.sendMessage(tf.of("Disabled printing"));
                break;
            case "arm":
                enabled = true;
                int count = 1;
                if (parts.length > 1) {
                    try {
                        count = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException ignored) {
                    }
                }
                armCount = count;
                source.sendMessage(tf.of("Armed for " + count + " prints"));
                break;
            default:
                return resultFactory.empty();
        }
        return resultFactory.empty();
    }

    private boolean checkPrint() {
        boolean ret = enabled;
        if (armCount > 0) {
            if (--armCount == 0) enabled = false;
        }
        return ret;
    }


}
