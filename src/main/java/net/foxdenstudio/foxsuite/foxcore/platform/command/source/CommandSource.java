package net.foxdenstudio.foxsuite.foxcore.platform.command.source;

import net.foxdenstudio.foxsuite.foxcore.platform.service.permission.Subject;
import net.foxdenstudio.foxsuite.foxcore.platform.text.channel.MessageReceiver;

public interface CommandSource extends Subject, MessageReceiver {

    String getName();

}
