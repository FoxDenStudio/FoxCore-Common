package net.foxdenstudio.foxcore.content.region;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.region.FoxRegionBase;

import javax.inject.Inject;

public class RectangularRegion extends FoxRegionBase<RectangularRegion> {

    private int lowerX;
    private int lowerZ;
    private int upperX;
    private int upperZ;

    @Inject
    private RectangularRegion(FoxArchetype archetype) {
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
}
