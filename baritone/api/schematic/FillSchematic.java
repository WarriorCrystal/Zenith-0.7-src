// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.schematic;

import java.util.Iterator;
import java.util.List;
import baritone.api.utils.BlockOptionalMeta;

public class FillSchematic extends AbstractSchematic
{
    private final BlockOptionalMeta bom;
    
    public FillSchematic(final int n, final int n2, final int n3, final BlockOptionalMeta bom) {
        super(n, n2, n3);
        this.bom = bom;
    }
    
    public FillSchematic(final int n, final int n2, final int n3, final awt awt) {
        this(n, n2, n3, new BlockOptionalMeta(awt.u(), awt.u().e(awt)));
    }
    
    public BlockOptionalMeta getBom() {
        return this.bom;
    }
    
    @Override
    public awt desiredState(final int n, final int n2, final int n3, final awt awt, final List<awt> list) {
        if (this.bom.matches(awt)) {
            return awt;
        }
        if (awt.u() != aox.a) {
            return aox.a.t();
        }
        for (final awt awt2 : list) {
            if (this.bom.matches(awt2)) {
                return awt2;
            }
        }
        return this.bom.getAnyBlockState();
    }
}
