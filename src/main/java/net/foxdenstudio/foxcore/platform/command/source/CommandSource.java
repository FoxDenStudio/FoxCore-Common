package net.foxdenstudio.foxcore.platform.command.source;

import net.foxdenstudio.foxcore.platform.service.permission.Subject;
import net.foxdenstudio.foxcore.platform.text.channel.MessageReceiver;

public interface CommandSource extends Subject, MessageReceiver {

    String getName();

}
