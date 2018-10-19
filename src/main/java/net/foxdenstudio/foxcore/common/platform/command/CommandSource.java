package net.foxdenstudio.foxcore.common.platform.command;

import net.foxdenstudio.foxcore.common.platform.service.permission.Subject;
import net.foxdenstudio.foxcore.common.platform.text.channel.MessageReciever;

public interface CommandSource extends Subject, MessageReciever {

    String getName();

}
