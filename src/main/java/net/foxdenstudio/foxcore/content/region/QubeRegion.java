package net.foxdenstudio.foxcore.content.region;

import net.foxdenstudio.foxcore.api.annotation.module.FoxGenerator;
import net.foxdenstudio.foxcore.api.archetype.ArchetypeBase;
import net.foxdenstudio.foxcore.api.archetype.type.FoxType;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.FoxDetailableObject;
import net.foxdenstudio.foxcore.api.object.generator.GeneratorObjectBase;
import net.foxdenstudio.foxcore.api.region.FoxRegionBase;
import net.foxdenstudio.foxcore.content.archetype.GeneratorArchetype;
import net.foxdenstudio.foxcore.content.archetype.RegionArchetype;
import net.foxdenstudio.foxcore.content.attribute.ArchetypeDisplayNameAttribute;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;
import net.foxdenstudio.foxcore.platform.fox.text.TextFactory;
import net.foxdenstudio.foxcore.platform.text.Text;
import net.foxdenstudio.foxcore.platform.text.format.TextColor;
import net.foxdenstudio.foxcore.platform.text.format.TextColors;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Random;

public class QubeRegion extends FoxRegionBase<QubeRegion.Type> implements FoxDetailableObject {

    private final TextFactory tf;
    private final TextColors tc;

    private int[] xBounds = {};
    private int[] yBounds = {};
    private int[] zBounds = {};

    private boolean[][][] volumes = {{{false}}};

    @Inject
    private QubeRegion(Type archetype, TextFactory textFactory, TextColors textColors) {
        super(archetype);
        this.tf = textFactory;
        this.tc = textColors;
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
                return val < bounds[0] ? 0 : (val < bounds[1] ? 1 : 2);
            default:
                int lower = 0;
                int upper = bounds.length;
                while (lower != upper) {
                    int bound = (lower + upper) >> 1;
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
                return val < bounds[0] ? 0 : (val < bounds[1] ? 1 : 2);
            default:
                int lower = 0;
                int upper = bounds.length;
                while (lower != upper) {
                    int bound = (lower + upper) >> 1;
                    if (val < bounds[bound]) {
                        upper = bound;
                    } else {
                        lower = bound + 1;
                    }
                }
                return lower;
        }
    }

    public boolean addXBound(int x) {
        for (int xb : this.xBounds) {
            if (xb == x) return false;
        }
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
                for (int y = 0; y < newVolumes[i].length; y++) {
                    newVolumes[i][y] = this.volumes[j][y].clone();
                }
                inserted = 1;
            } else {
                newVolumes[i] = this.volumes[j];
            }
        }
        this.volumes = newVolumes;

        return true;
    }

    public boolean addYBound(int y) {
        for (int yb : this.yBounds) {
            if (yb == y) return false;
        }
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

        return true;
    }

    public boolean addZBound(int z) {
        for (int zb : this.zBounds) {
            if (zb == z) return false;
        }
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

        return true;
    }

    // TODO add methods to move and remove bounds.

    /**
     * Moves a bound. Volumes are left unchanged. They are effectively "slid over".
     * If 'from' equals 'to', this function returns whether the bound exists and does nothing.
     *
     * @param axis which axis to operate on
     * @param from the original bound
     * @param to   the new bound
     * @return whether the operation was successful
     */
    public boolean moveBound(Axis axis, int from, int to) {
        int[] bounds;
        switch (axis) {
            case X:
                bounds = this.xBounds;
                break;
            case Y:
                bounds = this.yBounds;
                break;
            case Z:
                bounds = this.zBounds;
                break;
            default:
                return false;
        }

        int index = -1;
        for (int i = 0; i < bounds.length; i++) {
            if (bounds[i] == from) {
                index = i;
                break;
            }
        }
        if (index < 0) return false;

        if (from == to) return true;

        for (int bound : bounds) {
            if (bound == to) return false;
        }

        int i;
        if (to > from) {
            for (i = index; i < bounds.length - 1; i++) {
                int next = bounds[i + 1];
                if (next > to) break;
                bounds[i] = next;
            }
            bounds[i] = to;
        } else {
            for (i = index; i > 0; i--) {
                int prev = bounds[i - 1];
                if (prev < to) break;
                bounds[i] = prev;
            }
        }

        return true;
    }

    public boolean removeXBound(int x, Op strategy) {
        int index = -1;
        for (int i = 0; i < this.xBounds.length; i++) {
            if (this.xBounds[i] == x) {
                index = i;
                break;
            }
        }
        if (index < 0) return false;

        int[] newBounds = new int[this.xBounds.length - 1];
        int removed = 0;
        for (int i = 0; i < newBounds.length; i++) {
            int j = i + removed;
            if (removed == 0 && i == index) {
                removed = 1;
            }
            newBounds[i] = this.xBounds[j];
        }
        this.xBounds = newBounds;

        int merged = 0;
        boolean[][][] newVolumes = new boolean[this.volumes.length - 1][][];
        for (int i = 0; i < newVolumes.length; i++) {
            int j = i + merged;
            newVolumes[i] = this.volumes[j];
            if (merged == 0 && i == index) {
                j++;
                for (int y = 0; y < newVolumes[i].length; y++) {
                    for (int z = 0; z < newVolumes[i][y].length; z++) {
                        newVolumes[i][y][z] = strategy.op(newVolumes[i][y][z], this.volumes[j][y][z]);
                    }
                }
                merged = 1;
            }
        }
        this.volumes = newVolumes;

        return true;
    }

    public boolean removeYBound(int y, Op strategy) {
        int index = -1;
        for (int i = 0; i < this.yBounds.length; i++) {
            if (this.yBounds[i] == y) {
                index = i;
                break;
            }
        }
        if (index < 0) return false;

        int[] newBounds = new int[this.yBounds.length - 1];
        int removed = 0;
        for (int i = 0; i < newBounds.length; i++) {
            int j = i + removed;
            if (removed == 0 && i == index) {
                removed = 1;
            }
            newBounds[i] = this.yBounds[j];
        }
        this.yBounds = newBounds;

        for (int x = 0; x < this.volumes.length; x++) {
            boolean[][] newVolumes = new boolean[this.volumes[x].length - 1][];
            int merged = 0;
            for (int i = 0; i < newVolumes.length; i++) {
                int j = i + merged;
                newVolumes[i] = this.volumes[x][j];
                if (merged == 0 && i == index) {
                    j++;
                    for (int z = 0; z < newVolumes[i].length; z++) {
                        newVolumes[i][z] = strategy.op(newVolumes[i][z], this.volumes[x][j][z]);
                    }
                    merged = 1;
                }
            }
            this.volumes[x] = newVolumes;
        }

        return true;
    }

    public boolean removeZBound(int z, Op strategy) {
        int index = -1;
        for (int i = 0; i < this.zBounds.length; i++) {
            if (this.zBounds[i] == z) {
                index = i;
                break;
            }
        }
        if (index < 0) return false;

        int[] newBounds = new int[this.zBounds.length - 1];
        int removed = 0;
        for (int i = 0; i < newBounds.length; i++) {
            int j = i + removed;
            if (removed == 0 && i == index) {
                removed = 1;
            }
            newBounds[i] = this.zBounds[j];
        }
        this.zBounds = newBounds;

        for (int x = 0; x < this.volumes.length; x++) {
            for (int y = 0; y < this.volumes[x].length; y++) {
                boolean[] newVolumes = new boolean[this.volumes[x][y].length - 1];
                int merged = 0;
                for (int i = 0; i < newVolumes.length; i++) {
                    int j = i + merged;
                    newVolumes[i] = this.volumes[x][y][j];
                    if (merged == 0 && i == index) {
                        j++;
                        newVolumes[i] = strategy.op(newVolumes[i], this.volumes[x][y][j]);
                        merged = 1;
                    }
                }
                this.volumes[x][y] = newVolumes;
            }
        }

        return true;
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
                    boolean a = (input == (vx1 <= x && x <= vx2 && vy1 <= y && y <= vy2 && vz1 <= z && z <= vz2));
                    boolean b = this.volumes[x][y][z];
                    this.volumes[x][y][z] = op.op(a, b);
                }
            }
        }
    }

    @Override
    public Text details(CommandSource source, String arguments) {
        Text.Builder builder = tf.builder();
        if (this.xBounds.length == 0 && this.yBounds.length == 0 && this.zBounds.length == 0) {
            builder.append(tf.of(tc.GRAY, "No bounds\n", tc.YELLOW, "Mode: "));
            if (this.volumes[0][0][0]) {
                builder.append(tf.of(tc.GREEN, "All"));
            } else {
                builder.append(tf.of(tc.RED, "None"));
            }
        } else {
            if (this.xBounds.length != 0) {
                builder.append(tf.of(tc.RED, "X Bounds: ", tc.RESET, Arrays.toString(this.xBounds), "\n"));
            }
            if (this.yBounds.length != 0) {
                builder.append(tf.of(tc.GREEN, "Y Bounds: ", tc.RESET, Arrays.toString(this.yBounds), "\n"));
            }
            if (this.zBounds.length != 0) {
                builder.append(tf.of(tc.AQUA, "Z Bounds: ", tc.RESET, Arrays.toString(this.zBounds), "\n"));
            }
            builder.append(tf.of(tc.GOLD, "Mode: "));
            Mode mode = Mode.CUSTOM;
            if (this.xBounds.length == 0 && this.yBounds.length == 0 && this.zBounds.length == 0) {
                mode = Mode.SINGLE;
            } else if (this.xBounds.length == 2 && this.zBounds.length == 2) {
                if (this.yBounds.length == 2) {
                    boolean outside = false, inside = false;
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            for (int k = 0; k < 3; k++) {
                                if (i == 1 && j == 1 && k == 1) {
                                    inside = this.volumes[i][j][k];
                                } else {
                                    outside |= this.volumes[i][j][k];
                                }
                            }
                        }
                    }
                    if (inside && !outside) {
                        mode = Mode.BOX;
                    }
                } else if (this.yBounds.length == 0) {
                    boolean outside = false, inside = false;
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (i == 1 && j == 1) {
                                inside = this.volumes[i][0][j];
                            } else {
                                outside |= this.volumes[i][0][j];
                            }
                        }
                    }
                    if (inside && !outside) {
                        mode = Mode.RECT;
                    }
                }
            }
            if (mode == Mode.CUSTOM) {
                if (this.xBounds.length == 0) {
                    if (this.yBounds.length == 0) {
                        mode = Mode.Z;
                    } else if (this.zBounds.length == 0) {
                        mode = Mode.Y;
                    } else {
                        mode = Mode.YZ;
                    }
                } else if (this.yBounds.length == 0) {
                    if (this.zBounds.length == 0) {
                        mode = Mode.X;
                    } else {
                        mode = Mode.XZ;
                    }
                } else if (this.zBounds.length == 0) {
                    mode = Mode.XY;
                }
            }
            switch (mode) {
                case SINGLE:
                    builder.append(tf.of(tc.YELLOW, "Single - "));
                    if (volumes[0][0][0]) builder.append(tf.of(tc.GREEN, "On"));
                    else builder.append(tf.of(tc.RED, "Off"));
                case BOX:
                    builder.append(tf.of(tc.RESET, "Box"));
                    builder.append(tf.of("\n\n"));
                    builder.append(this.generateVisual(null));
                    break;
                case RECT:
                    builder.append(tf.of(tc.RESET, "Rectangle"));
                    builder.append(tf.of("\n"));
                    builder.append(this.generateVisual(Axis.Y));
                    break;
                case CUSTOM:
                    builder.append(tf.of(tc.LIGHT_PURPLE, "Custom"));
                    builder.append(tf.of("\n\n"));
                    builder.append(this.generateVisual(null));
                    break;
                case XY:
                    builder.append(tf.of(tc.RED, "X", tc.GREEN, "Y"));
                    builder.append(tf.of("\n"));
                    builder.append(this.generateVisual(Axis.Z));
                    break;
                case YZ:
                    builder.append(tf.of(tc.GREEN, "Y", tc.AQUA, "Z"));
                    builder.append(tf.of("\n"));
                    builder.append(this.generateVisual(Axis.X));
                    break;
                case XZ:
                    builder.append(tf.of(tc.RED, "X", tc.AQUA, "Z"));
                    builder.append(tf.of("\n"));
                    builder.append(this.generateVisual(Axis.Y));
                    break;
                case X:
                    builder.append(tf.of(tc.RED, "X"));
                    builder.append(tf.of("\n"));
                    builder.append(this.generateVisual(Axis.Z));
                    break;
                case Y:
                    builder.append(tf.of(tc.GREEN, "Y"));
                    builder.append(tf.of("\n"));
                    builder.append(this.generateVisual(Axis.X));
                    break;
                case Z:
                    builder.append(tf.of(tc.AQUA, "Z"));
                    builder.append(tf.of("\n"));
                    builder.append(this.generateVisual(Axis.X));
                    break;
            }

        }

        return builder.build();
    }

    private Text generateVisual(Axis axis) {
        return this.generateVisual(axis, -1, -1, -1, -1, -1, -1);
    }

    private Text generateVisual(Axis axis, int lowX, int lowY, int lowZ, int highX, int highY, int highZ) {
        if (lowX < 0 || lowX > this.xBounds.length) lowX = 0;
        if (lowY < 0 || lowY > this.yBounds.length) lowY = 0;
        if (lowZ < 0 || lowZ > this.zBounds.length) lowZ = 0;
        if (highX < 0 || highX > this.xBounds.length) highX = this.xBounds.length;
        if (highY < 0 || highY > this.yBounds.length) highY = this.yBounds.length;
        if (highZ < 0 || highZ > this.zBounds.length) highZ = this.zBounds.length;

        int[][] xCounts = {};
        int[][] yCounts = {};
        int[][] zCounts = {};
        int xRange;
        int yRange;
        int zRange;
        int highest = 0;

        if (axis == null || axis == Axis.X) {
            xRange = highX - lowX + 1;
            xCounts = new int[highY - lowY + 1][];
            for (int i = 0; i < xCounts.length; i++) {
                xCounts[i] = new int[highZ - lowZ + 1];
                for (int j = 0; j < xCounts[i].length; j++) {
                    int count = 0;
                    for (int k = 0; k < xRange; k++) {
                        if (this.volumes[lowX + k][highY - i][lowZ + j]) count++;
                    }
                    if (count > highest) highest = count;
                    xCounts[i][j] = count;
                }
            }
        }
        if (axis == null || axis == Axis.Y) {
            yRange = highY - lowY + 1;
            yCounts = new int[highZ - lowZ + 1][];
            for (int i = 0; i < yCounts.length; i++) {
                yCounts[i] = new int[highX - lowX + 1];
                for (int j = 0; j < yCounts[i].length; j++) {
                    int count = 0;
                    for (int k = 0; k < yRange; k++) {
                        if (this.volumes[lowX + j][lowY + k][highZ - i]) count++;
                    }
                    if (count > highest) highest = count;
                    yCounts[i][j] = count;
                }
            }
        }
        if (axis == null || axis == Axis.Z) {
            zRange = highZ - lowZ + 1;
            zCounts = new int[highY - lowY + 1][];
            for (int i = 0; i < zCounts.length; i++) {
                zCounts[i] = new int[highX - lowX + 1];
                for (int j = 0; j < zCounts[i].length; j++) {
                    int count = 0;
                    for (int k = 0; k < zRange; k++) {
                        if (this.volumes[lowX + j][highY - i][lowZ + k]) count++;
                    }
                    if (count > highest) highest = count;
                    zCounts[i][j] = count;
                }
            }
        }

        Text[] palette = this.archetype.getPalette(highest);

        if (palette == null) return tf.of(tc.GRAY, "Visualization too deep to generate");

        Text.Builder builder = tf.builder();

        if (axis != null) {
            int[][] counts2d = {};
            switch (axis) {
                case X:
                    counts2d = xCounts;
                    break;
                case Y:
                    counts2d = yCounts;
                    break;
                case Z:
                    counts2d = zCounts;
                    break;
            }
            builder.append(tf.of(tc.RESET, "View: "));
            builder.append(getName(axis));
            builder.append(tf.of(tc.RESET, "  Legend: "));
            builder.append(tf.of((Object[]) palette));
            builder.append(tf.of("\n"));

            for (int[] counts : counts2d) {
                builder.append(tf.of("\n"));
                for (int count : counts) {
                    builder.append(palette[count]);
                }
            }

        } else {
            for (int i = 0; i < zCounts.length; i++) {
                for (int j = 0; j < zCounts[i].length; j++) {
                    builder.append(palette[zCounts[i][j]]);
                }
                if (i == 0) {
                    builder.append(tf.of(tc.RESET, "  L: "));
                    builder.append(tf.of((Object[]) palette));
                }
                builder.append(tf.of("\n"));
            }

            for (int i = 0; i < yCounts.length; i++) {
                builder.append(tf.of("\n"));
                for (int j = 0; j < yCounts[i].length; j++) {
                    builder.append(palette[yCounts[i][j]]);
                }
                builder.append(tf.of("  "));
                for (int j = xCounts.length - 1; j >= 0; j--) {
                    builder.append(palette[xCounts[j][yCounts.length - i - 1]]);
                }
            }
        }
        return builder.build();
    }

    private Text getName(Axis axis) {
        if (axis == null) return tf.of(tc.YELLOW, "None");
        switch (axis) {
            case X:
                return tf.of(tc.RED, "X-Axis");
            case Y:
                return tf.of(tc.GREEN, "Y-Axis");
            case Z:
                return tf.of(tc.AQUA, "Z-Axis");
            default:
                return tf.of(tc.LIGHT_PURPLE, "<ERROR!>");
        }
    }

    private enum Axis {
        X, Y, Z;
    }

    public enum Mode {
        SINGLE, BOX, RECT, CUSTOM, XY, YZ, XZ, X, Y, Z
    }

    public enum Op {
        OR {
            @Override
            public boolean op(boolean a, boolean b) {
                return a | b;
            }
        },
        NOR {
            @Override
            public boolean op(boolean a, boolean b) {
                return !(a | b);
            }
        },
        AND {
            @Override
            public boolean op(boolean a, boolean b) {
                return a & b;
            }
        },
        NAND {
            @Override
            public boolean op(boolean a, boolean b) {
                return !(a & b);
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
        LEFT {
            @Override
            public boolean op(boolean a, boolean b) {
                return a;
            }
        },
        RIGHT {
            @Override
            public boolean op(boolean a, boolean b) {
                return b;
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
    public static class Type extends ArchetypeBase implements FoxType {

        private final TextColors tc;
        private final TextFactory tf;

        final TextColor[][] palette;
        final Text[][] textPalette;

        @Inject
        private Type(RegionArchetype regionArchetype,
                     ArchetypeDisplayNameAttribute archetypeDisplayNameAttribute,
                     TextColors tc, TextFactory tf) {
            super("qube", "Qube", regionArchetype, archetypeDisplayNameAttribute);
            this.tc = tc;
            this.tf = tf;
            this.writeDefaultName(archetypeDisplayNameAttribute);
            this.palette = new TextColor[][]{
                    {tc.DARK_GRAY},
                    {tc.DARK_GRAY, tc.GREEN},
                    {tc.DARK_GRAY, tc.RED, tc.GREEN},
                    {tc.DARK_GRAY, tc.RED, tc.YELLOW, tc.GREEN},
                    {tc.DARK_GRAY, tc.RED, tc.YELLOW, tc.GREEN, tc.AQUA},
                    {tc.DARK_GRAY, tc.RED, tc.YELLOW, tc.GREEN, tc.AQUA, tc.LIGHT_PURPLE},
                    {tc.DARK_GRAY, tc.RED, tc.YELLOW, tc.GREEN, tc.AQUA, tc.BLUE, tc.LIGHT_PURPLE},
                    {tc.DARK_GRAY, tc.RED, tc.GOLD, tc.YELLOW, tc.GREEN, tc.AQUA, tc.BLUE, tc.LIGHT_PURPLE},
                    {tc.DARK_GRAY, tc.GRAY, tc.RED, tc.GOLD, tc.YELLOW, tc.GREEN, tc.AQUA, tc.BLUE, tc.LIGHT_PURPLE},
                    {tc.BLACK, tc.DARK_GRAY, tc.GRAY, tc.RED, tc.GOLD, tc.YELLOW, tc.GREEN, tc.AQUA, tc.BLUE, tc.LIGHT_PURPLE},
                    {tc.BLACK, tc.DARK_GRAY, tc.GRAY, tc.RED, tc.GOLD, tc.YELLOW, tc.GREEN, tc.AQUA, tc.BLUE, tc.LIGHT_PURPLE, tc.WHITE},
                    {tc.BLACK, tc.DARK_GRAY, tc.GRAY, tc.DARK_RED, tc.RED, tc.GOLD, tc.YELLOW, tc.GREEN, tc.AQUA, tc.BLUE, tc.LIGHT_PURPLE, tc.WHITE},
                    {tc.BLACK, tc.DARK_GRAY, tc.GRAY, tc.DARK_RED, tc.RED, tc.GOLD, tc.YELLOW, tc.GREEN, tc.AQUA, tc.BLUE, tc.DARK_PURPLE, tc.LIGHT_PURPLE, tc.WHITE},
                    {tc.BLACK, tc.DARK_GRAY, tc.GRAY, tc.DARK_RED, tc.RED, tc.GOLD, tc.YELLOW, tc.GREEN, tc.DARK_AQUA, tc.AQUA, tc.BLUE, tc.DARK_PURPLE, tc.LIGHT_PURPLE, tc.WHITE},
                    {tc.BLACK, tc.DARK_GRAY, tc.GRAY, tc.DARK_RED, tc.RED, tc.GOLD, tc.YELLOW, tc.DARK_GREEN, tc.GREEN, tc.DARK_AQUA, tc.AQUA, tc.BLUE, tc.DARK_PURPLE, tc.LIGHT_PURPLE, tc.WHITE},
                    {tc.BLACK, tc.DARK_GRAY, tc.GRAY, tc.DARK_RED, tc.RED, tc.GOLD, tc.YELLOW, tc.DARK_GREEN, tc.GREEN, tc.DARK_AQUA, tc.AQUA, tc.DARK_BLUE, tc.BLUE, tc.DARK_PURPLE, tc.LIGHT_PURPLE, tc.WHITE},
            };
            this.textPalette = new Text[this.palette.length][];
        }

        Text[] getPalette(int max) {
            if (max >= this.palette.length) return null;

            Text[] ret = this.textPalette[max];
            if (ret == null) {
                TextColor[] colors = this.palette[max];

                ret = new Text[max + 1];
                for (int i = 0; i <= max; i++) {
                    ret[i] = tf.of(colors[i], "O");
                }
                this.textPalette[max] = ret;
            }

            return ret;
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
                    coords[i] = Integer.parseInt(args[i].trim());
                } catch (NumberFormatException e) {
                    throw new FoxCommandException("\"" + args[i].trim() + "\" is not an integer!", e);
                }
            }
            QubeRegion region = provider.get();
            region.addXBound(coords[0]);
            region.addXBound(coords[2]);
            region.addZBound(coords[1]);
            region.addZBound(coords[3]);
            region.addVolume(1, 0, 1);
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
                    coords[i] = Integer.parseInt(args[i].trim());
                } catch (NumberFormatException e) {
                    throw new FoxCommandException("\"" + args[i].trim() + "\" is not an integer!", e);
                }
            }
            QubeRegion region = provider.get();
            region.addXBound(coords[0]);
            region.addXBound(coords[3]);
            region.addYBound(coords[1]);
            region.addYBound(coords[4]);
            region.addZBound(coords[2]);
            region.addZBound(coords[5]);
            region.addVolume(1, 1, 1);
            return region;
        }
    }

    @Singleton
    @FoxGenerator
    public static class FLARDGenerator extends GeneratorObjectBase<QubeRegion> {

        private final Provider<QubeRegion> provider;

        @Inject
        private FLARDGenerator(GeneratorArchetype archetype, Provider<QubeRegion> provider) {
            super(archetype);
            this.provider = provider;
        }

        @Override
        public QubeRegion generate(CommandSource source, String arguments) throws FoxCommandException {
            String[] args = arguments.split(" +");
            if (args.length < 6)
                throw new FoxCommandException("Must specify 6 integer coordinates! <x1> <y1> <z1> <x2> <y2> <z2>");
            int[] coords = new int[6];
            for (int i = 0; i < 6; i++) {
                try {
                    coords[i] = Integer.parseInt(args[i].trim());
                } catch (NumberFormatException e) {
                    throw new FoxCommandException("\"" + args[i].trim() + "\" is not an integer!", e);
                }
            }
            boolean infinite = args.length > 6 && args[6].trim().equalsIgnoreCase("inf");

            int cutoff = infinite ? 4 : 6;
            int xRange = Math.abs(coords[0] - coords[3]);
            int yRange = Math.abs(coords[1] - coords[4]);
            int zRange = Math.abs(coords[2] - coords[5]);

            QubeRegion region = provider.get();

            region.addXBound(coords[0]);
            region.addXBound(coords[3]);
            region.addYBound(coords[1]);
            region.addYBound(coords[4]);
            region.addZBound(coords[2]);
            region.addZBound(coords[5]);


            Random random = new Random();

            if (infinite) {

                if (xRange < cutoff) {
                    int min = Math.min(coords[0], coords[3]);
                    for (int i = 1; i < xRange; i++) {
                        region.addXBound(min + i);
                    }
                } else {
                    region.addXBound((3 * coords[0] + coords[3]) / 4);
                    region.addXBound((coords[0] + coords[3]) / 2);
                    region.addXBound((coords[0] + 3 * coords[3]) / 4);
                }

                if (yRange < cutoff) {
                    int min = Math.min(coords[1], coords[4]);
                    for (int i = 1; i < yRange; i++) {
                        region.addYBound(min + i);
                    }
                } else {
                    region.addYBound((3 * coords[1] + coords[4]) / 4);
                    region.addYBound((coords[1] + coords[4]) / 2);
                    region.addYBound((coords[1] + 3 * coords[4]) / 4);
                }

                if (zRange < cutoff) {
                    int min = Math.min(coords[2], coords[5]);
                    for (int i = 1; i < zRange; i++) {
                        region.addZBound(min + i);
                    }
                } else {
                    region.addZBound((3 * coords[2] + coords[5]) / 4);
                    region.addZBound((coords[2] + coords[5]) / 2);
                    region.addZBound((coords[2] + 3 * coords[5]) / 4);
                }

                xRange = Math.min(xRange, cutoff);
                yRange = Math.min(yRange, cutoff);
                zRange = Math.min(zRange, cutoff);

                for (int i = 0; i < xRange + 2; i++) {
                    for (int j = 0; j < yRange + 2; j++) {
                        for (int k = 0; k < zRange + 2; k++) {
                            if (random.nextBoolean()) {
                                region.addVolume(i, j, k);
                            }
                        }
                    }
                }
            } else {
                if (xRange < cutoff) {
                    int min = Math.min(coords[0], coords[3]);
                    for (int i = 1; i < xRange; i++) {
                        region.addXBound(min + i);
                    }
                } else {
                    region.addXBound((5 * coords[0] + coords[3]) / 6);
                    region.addXBound((2 * coords[0] + coords[3]) / 3);
                    region.addXBound((coords[0] + coords[3]) / 2);
                    region.addXBound((coords[0] + 2 * coords[3]) / 3);
                    region.addXBound((coords[0] + 5 * coords[3]) / 6);
                }

                if (yRange < cutoff) {
                    int min = Math.min(coords[1], coords[4]);
                    for (int i = 1; i < yRange; i++) {
                        region.addYBound(min + i);
                    }
                } else {
                    region.addYBound((5 * coords[1] + coords[4]) / 6);
                    region.addYBound((2 * coords[1] + coords[4]) / 3);
                    region.addYBound((coords[1] + coords[4]) / 2);
                    region.addYBound((coords[1] + 2 * coords[4]) / 3);
                    region.addYBound((coords[1] + 5 * coords[4]) / 6);
                }

                if (zRange < cutoff) {
                    int min = Math.min(coords[2], coords[5]);
                    for (int i = 1; i < zRange; i++) {
                        region.addZBound(min + i);
                    }
                } else {
                    region.addZBound((5 * coords[2] + coords[5]) / 6);
                    region.addZBound((2 * coords[2] + coords[5]) / 3);
                    region.addZBound((coords[2] + coords[5]) / 2);
                    region.addZBound((coords[2] + 2 * coords[5]) / 3);
                    region.addZBound((coords[2] + 5 * coords[5]) / 6);
                }

                xRange = Math.min(xRange, cutoff);
                yRange = Math.min(yRange, cutoff);
                zRange = Math.min(zRange, cutoff);

                for (int i = 1; i < xRange + 1; i++) {
                    for (int j = 1; j < yRange + 1; j++) {
                        for (int k = 1; k < zRange + 1; k++) {
                            if (random.nextBoolean()) {
                                region.addVolume(i, j, k);
                            }
                        }
                    }
                }
            }

            return region;
        }
    }
}
