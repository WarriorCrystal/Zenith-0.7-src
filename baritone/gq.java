// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.List;
import baritone.api.schematic.IStaticSchematic;
import baritone.api.schematic.AbstractSchematic;

public class gq extends AbstractSchematic implements IStaticSchematic
{
    protected awt[][][] a;
    
    @Override
    public awt desiredState(final int n, final int n2, final int n3, final awt awt, final List<awt> list) {
        return this.a[n][n3][n2];
    }
    
    @Override
    public awt getDirect(final int n, final int n2, final int n3) {
        return this.a[n][n3][n2];
    }
    
    @Override
    public awt[] getColumn(final int n, final int n2) {
        return this.a[n][n2];
    }
}
