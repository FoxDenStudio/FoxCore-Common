package net.foxdenstudio.foxcore.content.region;

import net.foxdenstudio.foxcore.api.annotation.module.FoxGenerator;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.generator.GeneratorObjectBase;
import net.foxdenstudio.foxcore.api.region.FoxRegionBase;
import net.foxdenstudio.foxcore.content.archetype.GeneratorArchetype;
import net.foxdenstudio.foxcore.content.archetype.RegionArchetype;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Optional;

public class RectangularRegion extends FoxRegionBase<RectangularRegion> {

    private int lowerX;
    private int lowerZ;
    private int upperX;
    private int upperZ;

    @Inject
    private RectangularRegion(RegionArchetype archetype) {
        super(archetype);
    }

    public int getLowerX() {
        return lowerX;
    }

    public void setLowerX(int lowerX) {
        this.lowerX = lowerX;
    }

    public int getLowerZ() {
        return lowerZ;
    }

    public void setLowerZ(int lowerZ) {
        this.lowerZ = lowerZ;
    }

    public int getUpperX() {
        return upperX;
    }

    public void setUpperX(int upperX) {
        this.upperX = upperX;
    }

    public int getUpperZ() {
        return upperZ;
    }

    public void setUpperZ(int upperZ) {
        this.upperZ = upperZ;
    }

    public void setBounds(int lowerX, int lowerZ, int upperX, int upperZ){
        this.lowerX = lowerX;
        this.lowerZ = lowerZ;
        this.upperX = upperX;
        this.upperZ = upperZ;
    }

    @Override
    public boolean contains(int x, int y, int z) {
        return x >= this.lowerX && x <= this.upperX && z >= this.lowerZ && z <= this.upperZ;
    }

    @Singleton
    @FoxGenerator
    public static class Generator extends GeneratorObjectBase<Generator, RectangularRegion> {

        private final Provider<RectangularRegion> provider;

        @Inject
        private Generator(GeneratorArchetype archetype, Provider<RectangularRegion> provider) {
            super(archetype);
            this.provider = provider;
        }

        @Override
        public Optional<RectangularRegion> generate(CommandSource source, String arguments) throws FoxCommandException {
            String[] args = arguments.split(" +");
            if(args.length != 4) throw new FoxCommandException("Must specify 4 integer coordinates! <lower X> <lower Z> <upper X> <upper Z>");
            int[] coords = new int[4];
            for (int i = 0; i < 4; i++) {
                try{
                    coords[i] = Integer.parseInt(args[i]);
                } catch (NumberFormatException e){
                    throw new FoxCommandException("\"" + args[i] + "\" is not an integer!", e);
                }
            }
            RectangularRegion region = provider.get();
            region.setBounds(coords[0], coords[1], coords[2], coords[3]);
            return Optional.of(region);
        }
    }
}
