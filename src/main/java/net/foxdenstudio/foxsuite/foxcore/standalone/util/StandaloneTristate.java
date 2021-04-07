package net.foxdenstudio.foxsuite.foxcore.standalone.util;

import net.foxdenstudio.foxsuite.foxcore.platform.util.Tristate;

public enum StandaloneTristate implements Tristate {
    TRUE(true) {
        @Override
        public Tristate and(Tristate other) {
            return other == TRUE || other == UNDEFINED ? TRUE : FALSE;
        }

        @Override
        public Tristate or(Tristate other) {
            return TRUE;
        }
    },
    FALSE(false) {
        @Override
        public Tristate and(Tristate other) {
            return FALSE;
        }

        @Override
        public Tristate or(Tristate other) {
            return other == TRUE ? TRUE : FALSE;
        }
    },
    UNDEFINED(false) {
        @Override
        public Tristate and(Tristate other) {
            return other;
        }

        @Override
        public Tristate or(Tristate other) {
            return other;
        }
    };

    private final boolean val;

    StandaloneTristate(boolean val) {
        this.val = val;
    }


    @Override
    public boolean asBoolean() {
        return this.val;
    }
}
