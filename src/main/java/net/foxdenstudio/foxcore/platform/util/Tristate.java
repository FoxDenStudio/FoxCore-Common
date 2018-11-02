package net.foxdenstudio.foxcore.platform.util;

public interface Tristate {

    Tristate and(Tristate other);

    Tristate or(Tristate other);

    boolean asBoolean();
}
