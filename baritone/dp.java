// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.movement.MovementStatus;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import baritone.api.utils.BetterBlockPos;
import baritone.api.IBaritone;

public final class dp extends ck
{
    private int a;
    
    public dp(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        super(baritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[] { betterBlockPos2 });
        this.a = 0;
    }
    
    @Override
    public final void reset() {
        super.reset();
        this.a = 0;
    }
    
    @Override
    public final double a(final cj cj) {
        return a(cj, this.a.a, this.a.b, this.a.c);
    }
    
    public final Set<BetterBlockPos> a() {
        return (Set<BetterBlockPos>)ImmutableSet.of((Object)this.a, (Object)this.b);
    }
    
    public static double a(final cj cj, final int n, final int n2, final int n3) {
        if (!cj.m) {
            return 1000000.0;
        }
        if (!cl.b(cj.a, n, n2 - 2, n3)) {
            return 1000000.0;
        }
        final awt a;
        final aow u;
        if ((u = (a = cj.a(n, n2 - 1, n3)).u()) == aox.au || u == aox.bn) {
            return 6.666666666666667;
        }
        return dp.FALL_N_BLOCKS_COST[1] + cl.a(cj, n, n2 - 1, n3, a, false);
    }
    
    @Override
    public final cn a(final cn cn) {
        super.a(cn);
        if (cn.a != MovementStatus.RUNNING) {
            return cn;
        }
        if (this.a.playerFeet().equals(this.b)) {
            cn.a = MovementStatus.SUCCESS;
            return cn;
        }
        if (!this.a()) {
            cn.a = MovementStatus.UNREACHABLE;
            return cn;
        }
        final double n = this.a.player().p - (this.b.p() + 0.5);
        final double n2 = this.a.player().r - (this.b.r() + 0.5);
        final double n3 = n;
        final double n4 = n3 * n3;
        final double n5 = n2;
        final double sqrt = Math.sqrt(n4 + n5 * n5);
        if (this.a++ < 10 && sqrt < 0.2) {
            return cn;
        }
        cl.a(this.a, cn, this.a[0]);
        return cn;
    }
}
