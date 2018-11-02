package net.foxdenstudio.foxcore.standalone.fox.util;

import net.foxdenstudio.foxcore.platform.fox.util.TristateFactory;
import net.foxdenstudio.foxcore.platform.util.Tristate;
import net.foxdenstudio.foxcore.standalone.util.StandaloneTristate;

import javax.inject.Singleton;

@Singleton
public class StandaloneTristateFactory implements TristateFactory {

    @Override
    public Tristate getTrue() {
        return StandaloneTristate.TRUE;
    }

    @Override
    public Tristate getFalse() {
        return StandaloneTristate.FALSE;
    }

    @Override
    public Tristate getUndefined() {
        return StandaloneTristate.UNDEFINED;
    }

    @Override
    public Tristate fromBoolean(boolean value) {
        return value ? StandaloneTristate.TRUE : StandaloneTristate.FALSE;
    }
}
