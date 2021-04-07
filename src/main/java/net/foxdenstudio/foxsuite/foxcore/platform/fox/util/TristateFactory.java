package net.foxdenstudio.foxsuite.foxcore.platform.fox.util;

import net.foxdenstudio.foxsuite.foxcore.platform.util.Tristate;

/**
 * Factory for getting {@link Tristate} instances.
 *
 * This exists solely so that the SpongeAPI tristate enum can be reused, to prevent wrapper/converter hell.
 * All returned tristates need to be instance-equal, so that they can be used like booleans.
 */
public interface TristateFactory {

    Tristate getTrue();

    Tristate getFalse();

    Tristate getUndefined();

    Tristate fromBoolean(boolean value);
}
