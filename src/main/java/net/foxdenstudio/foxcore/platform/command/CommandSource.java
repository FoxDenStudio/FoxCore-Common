package net.foxdenstudio.foxcore.platform.command;

import net.foxdenstudio.foxcore.platform.service.permission.Subject;
import net.foxdenstudio.foxcore.platform.text.channel.MessageReciever;

public interface CommandSource extends Subject, MessageReciever {

    String getName();

}
