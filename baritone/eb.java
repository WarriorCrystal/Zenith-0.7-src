// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.List;
import baritone.api.schematic.ISchematic;

final class eb implements ISchematic
{
    private /* synthetic */ ISchematic a;
    private /* synthetic */ int a;
    private /* synthetic */ int b;
    private /* synthetic */ ea a;
    
    eb(final ea a, final ISchematic a2, final int a3, final int b) {
        this.a = a;
        this.a = a2;
        this.a = a3;
        this.b = b;
    }
    
    @Override
    public final awt desiredState(final int n, final int n2, final int n3, final awt awt, final List<awt> list) {
        return this.a.desiredState(n, n2, n3, awt, this.a.a);
    }
    
    @Override
    public final boolean inSchematic(final int n, final int n2, final int n3, final awt awt) {
        return super.inSchematic(n, n2, n3, awt) && n2 >= this.a && n2 <= this.b && this.a.inSchematic(n, n2, n3, awt);
    }
    
    @Override
    public final int widthX() {
        return this.a.widthX();
    }
    
    @Override
    public final int heightY() {
        return this.a.heightY();
    }
    
    @Override
    public final int lengthZ() {
        return this.a.lengthZ();
    }
}
