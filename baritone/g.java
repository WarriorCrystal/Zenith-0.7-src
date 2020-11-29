// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.utils.BetterBlockPos;
import baritone.api.pathing.goals.Goal;

public final class g
{
    public final int a;
    public final int b;
    public final int c;
    public final double a;
    public double b;
    public double c;
    public g a;
    public int d;
    
    public g(final int a, final int b, final int c, final Goal obj) {
        this.a = null;
        this.b = 1000000.0;
        this.a = obj.heuristic(a, b, c);
        if (Double.isNaN(this.a)) {
            throw new IllegalStateException(obj + " calculated implausible heuristic");
        }
        this.d = -1;
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    @Override
    public final int hashCode() {
        return (int)BetterBlockPos.longHash(this.a, this.b, this.c);
    }
    
    @Override
    public final boolean equals(final Object o) {
        final g g = (g)o;
        return this.a == g.a && this.b == g.b && this.c == g.c;
    }
}
