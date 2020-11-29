// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.schematic;

import baritone.api.utils.BlockOptionalMetaLookup;

public class ReplaceSchematic extends MaskSchematic
{
    private final BlockOptionalMetaLookup filter;
    private final Boolean[][][] cache;
    
    public ReplaceSchematic(final ISchematic schematic, final BlockOptionalMetaLookup filter) {
        super(schematic);
        this.filter = filter;
        this.cache = new Boolean[this.widthX()][this.heightY()][this.lengthZ()];
    }
    
    @Override
    protected boolean partOfMask(final int n, final int n2, final int n3, final awt awt) {
        if (this.cache[n][n2][n3] == null) {
            this.cache[n][n2][n3] = this.filter.has(awt);
        }
        return this.cache[n][n2][n3];
    }
}
