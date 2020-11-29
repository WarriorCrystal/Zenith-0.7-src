// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.schematic;

public class WallsSchematic extends MaskSchematic
{
    public WallsSchematic(final ISchematic schematic) {
        super(schematic);
    }
    
    @Override
    protected boolean partOfMask(final int n, final int n2, final int n3, final awt awt) {
        return n == 0 || n3 == 0 || n == this.widthX() - 1 || n3 == this.lengthZ() - 1;
    }
}
