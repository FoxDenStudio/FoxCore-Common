package net.foxdenstudio.foxcore.api.object.link.slot;

import net.foxdenstudio.foxcore.api.object.FoxObject;

public interface LinkSlotSchema {

    LinkSlot createSlotInstance(FoxObject object);
}
