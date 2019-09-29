package net.foxdenstudio.foxcore.content.region;

import net.foxdenstudio.foxcore.api.annotation.module.FoxGenerator;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.generator.GeneratorObjectBase;
import net.foxdenstudio.foxcore.api.region.FoxRegionBase;
import net.foxdenstudio.foxcore.content.archetype.GeneratorArchetype;
import net.foxdenstudio.foxcore.content.archetype.RegionArchetype;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

public class QubeRegion extends FoxRegionBase {


    private int[] xBounds = {};
    private int[] yBounds = {};
    private int[] zBounds = {};

    private boolean[][][] volumes = {{{false}}};

    @Inject
    private QubeRegion(RegionArchetype archetype) {
        super(archetype);
    }


    @Override
    public boolean containsBlock(int x, int y, int z) {
        int vx = getSlice(xBounds, x);
        int vy = getSlice(yBounds, y);
        int vz = getSlice(zBounds, z);
        return this.volumes[vx][vy][vz];
    }

    @Override
    public boolean contains(double x, double y, double z) {
        int vx = getSlice(xBounds, x);
        int vy = getSlice(yBounds, y);
        int vz = getSlice(zBounds, z);
        return this.volumes[vx][vy][vz];
    }

    private static int getSlice(int[] bounds, int val) {
        switch (bounds.length) {
            case 0:
                return 0;
            case 1:
                return val < bounds[0] ? 0 : 1;
            case 2:
                return val < bounds[0] ? 0 : (val >= bounds[1] ? 2 : 1);
            default:
                int lower = 0;
                int upper = bounds.length;
                while (lower != upper) {
                    int bound = (lower + upper) / 2;
                    if (val < bounds[bound]) {
                        upper = bound;
                    } else {
                        lower = bound + 1;
                    }
                }
                return lower;
        }
    }

    private static int getSlice(int[] bounds, double val) {
        switch (bounds.length) {
            case 0:
                return 0;
            case 1:
                return val < bounds[0] ? 0 : 1;
            case 2:
                return val < bounds[0] ? 0 : (val >= bounds[1] ? 2 : 1);
            default:
                int lower = 0;
                int upper = bounds.length;
                while (lower != upper) {
                    int bound = (lower + upper) / 2;
                    if (val < bounds[bound]) {
                        upper = bound;
                    } else {
                        lower = bound + 1;
                    }
                }
                return lower;
        }
    }

    public void addXBound(int x) {
        int[] newBounds = new int[this.xBounds.length + 1];
        int inserted = 0;
        int pos = 0;
        for (int i = 0; i < newBounds.length; i++) {
            int j = i - inserted;
            if (inserted == 0 && (j == this.xBounds.length || x < this.xBounds[j])) {
                newBounds[i] = x;
                pos = i;
                inserted = 1;
            } else {
                newBounds[i] = this.xBounds[j];
            }
        }
        this.xBounds = newBounds;

        inserted = 0;
        boolean[][][] newVolumes = new boolean[this.volumes.length + 1][][];
        for (int i = 0; i < newVolumes.length; i++) {
            int j = i - inserted;
            if (inserted == 0 && i == pos) {
                newVolumes[i] = new boolean[this.volumes[j].length][];
                for (int k = 0; k < newVolumes[i].length; k++) {
                    newVolumes[i][k] = this.volumes[j][k].clone();
                }
                inserted = 1;
            } else {
                newVolumes[i] = this.volumes[j];
            }
        }
        this.volumes = newVolumes;
    }

    public void addYBound(int y) {
        int[] newBounds = new int[this.yBounds.length + 1];
        int inserted = 0;
        int pos = 0;
        for (int i = 0; i < newBounds.length; i++) {
            int j = i - inserted;
            if (inserted == 0 && (j == this.yBounds.length || y < this.yBounds[j])) {
                newBounds[i] = y;
                pos = i;
                inserted = 1;
            } else {
                newBounds[i] = this.yBounds[j];
            }
        }
        this.yBounds = newBounds;

        for (int x = 0; x < this.volumes.length; x++) {
            boolean[][] newVolumes = new boolean[this.volumes[x].length + 1][];
            inserted = 0;
            for (int i = 0; i < newVolumes.length; i++) {
                int j = i - inserted;
                if (inserted == 0 && i == pos) {
                    newVolumes[i] = this.volumes[x][j].clone();
                    inserted = 1;
                } else {
                    newVolumes[i] = this.volumes[x][j];
                }
            }
            this.volumes[x] = newVolumes;
        }
    }

    public void addZBound(int z) {
        int[] newBounds = new int[this.zBounds.length + 1];
        int inserted = 0;
        int pos = 0;
        for (int i = 0; i < newBounds.length; i++) {
            int j = i - inserted;
            if (inserted == 0 && (j == this.zBounds.length || z < this.zBounds[j])) {
                newBounds[i] = z;
                pos = i;
                inserted = 1;
            } else {
                newBounds[i] = this.zBounds[j];
            }
        }
        this.zBounds = newBounds;

        for (int x = 0; x < this.volumes.length; x++) {
            for (int y = 0; y < this.volumes[x].length; y++) {
                boolean[] newVolumes = new boolean[this.volumes[x][y].length + 1];
                inserted = 0;
                for (int i = 0; i < newVolumes.length; i++) {
                    int j = i - inserted;
                    newVolumes[i] = this.volumes[x][y][j];
                    if (inserted == 0 && i == pos) {
                        inserted = 1;
                    }
                }
                this.volumes[x][y] = newVolumes;
            }
        }
    }

    public void addVolume(int vx, int vy, int vz) {
        this.changeVolume(vx, vy, vz, vx, vy, vz, Op.OR, true);
    }

    public void removeVolume(int vx, int vy, int vz) {
        this.changeVolume(vx, vy, vz, vx, vy, vz, Op.AND, false);
    }

    public void changeVolume(int vx1, int vy1, int vz1, int vx2, int vy2, int vz2, Op op, boolean input) {
        for (int x = 0; x < this.volumes.length; x++) {
            for (int y = 0; y < this.volumes[x].length; y++) {
                for (int z = 0; z < this.volumes[x][y].length; z++) {
                    boolean a = this.volumes[x][y][z];
                    boolean b = (input == (vx1 <= x && x <= vx2 && vy1 <= y && y <= vy2 && vz1 <= z && z <= vz2));
                    this.volumes[x][y][z] = op.op(a, b);
                }
            }
        }
    }

    public enum Op {
        OR {
            @Override
            public boolean op(boolean a, boolean b) {
                return a || b;
            }
        },
        NOR {
            @Override
            public boolean op(boolean a, boolean b) {
                return !(a || b);
            }
        },
        AND {
            @Override
            public boolean op(boolean a, boolean b) {
                return a && b;
            }
        },
        NAND {
            @Override
            public boolean op(boolean a, boolean b) {
                return !(a && b);
            }
        },
        XOR {
            @Override
            public boolean op(boolean a, boolean b) {
                return a ^ b;
            }
        },
        EQ {
            @Override
            public boolean op(boolean a, boolean b) {
                return a == b;
            }
        },
        ;

        public abstract boolean op(boolean a, boolean b);

        @Nullable
        public static Op fromName(String name) {
            for (Op op : values()) {
                if (name.equalsIgnoreCase(op.name())) return op;
            }
            return null;
        }
    }

    @Singleton
    @FoxGenerator
    public static class RectGenerator extends GeneratorObjectBase<QubeRegion> {

        private final Provider<QubeRegion> provider;

        @Inject
        private RectGenerator(GeneratorArchetype archetype, Provider<QubeRegion> provider) {
            super(archetype);
            this.provider = provider;
        }

        @Override
        public QubeRegion generate(CommandSource source, String arguments) throws FoxCommandException {
            String[] args = arguments.split(" +");
            if (args.length != 4)
                throw new FoxCommandException("Must specify 4 integer coordinates! <x1> <z1> <x2> <z2>");
            int[] coords = new int[4];
            for (int i = 0; i < 4; i++) {
                try {
                    coords[i] = Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {
                    throw new FoxCommandException("\"" + args[i] + "\" is not an integer!", e);
                }
            }
            QubeRegion region = provider.get();
            region.addXBound(coords[0]);
            region.addXBound(coords[2]);
            region.addZBound(coords[1]);
            region.addZBound(coords[3]);
            region.addVolume(1,0,1);
            return region;
        }
    }

    @Singleton
    @FoxGenerator
    public static class BoxGenerator extends GeneratorObjectBase<QubeRegion> {

        private final Provider<QubeRegion> provider;

        @Inject
        private BoxGenerator(GeneratorArchetype archetype, Provider<QubeRegion> provider) {
            super(archetype);
            this.provider = provider;
        }

        @Override
        public QubeRegion generate(CommandSource source, String arguments) throws FoxCommandException {
            String[] args = arguments.split(" +");
            if (args.length != 6)
                throw new FoxCommandException("Must specify 6 integer coordinates! <x1> <y1> <z1> <x2> <y2> <z2>");
            int[] coords = new int[6];
            for (int i = 0; i < 6; i++) {
                try {
                    coords[i] = Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {
                    throw new FoxCommandException("\"" + args[i] + "\" is not an integer!", e);
                }
            }
            QubeRegion region = provider.get();
            region.addXBound(coords[0]);
            region.addXBound(coords[3]);
            region.addYBound(coords[1]);
            region.addYBound(coords[4]);
            region.addZBound(coords[2]);
            region.addZBound(coords[5]);
            region.addVolume(1,1,1);
            return region;
        }
    }
}
