// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.schematic;

import java.util.List;

public abstract class MaskSchematic extends AbstractSchematic
{
    private final ISchematic schematic;
    
    public MaskSchematic(final ISchematic schematic) {
        super(schematic.widthX(), schematic.heightY(), schematic.lengthZ());
        this.schematic = schematic;
    }
    
    protected abstract boolean partOfMask(final int p0, final int p1, final int p2, final awt p3);
    
    @Override
    public boolean inSchematic(final int n, final int n2, final int n3, final awt awt) {
        return this.schematic.inSchematic(n, n2, n3, awt) && this.partOfMask(n, n2, n3, awt);
    }
    
    @Override
    public awt desiredState(final int n, final int n2, final int n3, final awt awt, final List<awt> list) {
        return this.schematic.desiredState(n, n2, n3, awt, list);
    }
}
