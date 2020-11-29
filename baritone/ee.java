// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.IBaritone;
import baritone.api.schematic.ISchematic;
import java.util.List;

public final class ee extends cj
{
    private final List<awt> a;
    private final ISchematic a;
    private final int c;
    private final int d;
    private final int e;
    private /* synthetic */ ea a;
    
    public ee(final ea a) {
        this.a = a;
        super(a.a, true);
        this.a = (List<awt>)ea.b(a);
        this.a = a.a;
        this.c = a.a.p();
        this.d = a.a.q();
        this.e = a.a.r();
        this.e += 10.0;
        this.d = 1.0;
    }
    
    private awt a(final int n, final int n2, final int n3, final awt awt) {
        if (this.a.inSchematic(n - this.c, n2 - this.d, n3 - this.e, awt)) {
            return this.a.desiredState(n - this.c, n2 - this.d, n3 - this.e, awt, this.a.a);
        }
        return null;
    }
    
    @Override
    public final double a(final int n, final int n2, final int n3, final awt awt) {
        if (!this.a.b(n, n3)) {
            return 1000000.0;
        }
        final awt a;
        if ((a = this.a(n, n2, n3, awt)) != null) {
            if (a.u() == aox.a) {
                return this.a * 2.0;
            }
            if (this.a.contains(a)) {
                return 0.0;
            }
            if (!this.c) {
                return 1000000.0;
            }
            return this.a * 3.0;
        }
        else {
            if (this.c) {
                return this.a;
            }
            return 1000000.0;
        }
    }
    
    @Override
    public final double b(final int n, final int n2, final int n3, awt a) {
        if (!this.e) {
            return 1000000.0;
        }
        if ((a = this.a(n, n2, n3, a)) == null) {
            return 1.0;
        }
        if (a.u() == aox.a) {
            return 1.0;
        }
        if (ea.a(this.a.a(n, n2, n3), a)) {
            return a.a().breakCorrectBlockPenaltyMultiplier.value;
        }
        return 1.0;
    }
}
