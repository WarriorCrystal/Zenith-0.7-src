// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.utils.input.Input;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.Rotation;
import baritone.api.pathing.movement.MovementStatus;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import baritone.api.utils.BetterBlockPos;
import baritone.api.IBaritone;

public final class dn extends ck
{
    private int a;
    
    public dn(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        super(baritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[] { betterBlockPos2.up(2), betterBlockPos2.up(), betterBlockPos2 }, betterBlockPos2.down());
        this.a = 0;
    }
    
    @Override
    public final void reset() {
        super.reset();
        this.a = 0;
    }
    
    @Override
    public final double a(final cj cj) {
        final gj gj = new gj();
        a(cj, this.a.a, this.a.b, this.a.c, this.b.a, this.b.c, gj);
        if (gj.b != this.b.b) {
            return 1000000.0;
        }
        return gj.a;
    }
    
    public final Set<BetterBlockPos> a() {
        return (Set<BetterBlockPos>)ImmutableSet.of((Object)this.a, (Object)this.b.up(), (Object)this.b);
    }
    
    public static void a(final cj cj, final int n, final int n2, final int n3, final int a, final int c, final gj gj) {
        final awt a2 = cj.a(a, n2 - 1, c);
        final double n4;
        if ((n4 = 0.0 + cl.a(cj, a, n2 - 1, c, a2, false)) >= 1000000.0) {
            return;
        }
        final double n5;
        if ((n5 = n4 + cl.a(cj, a, n2, c, false)) >= 1000000.0) {
            return;
        }
        final double n6;
        if ((n6 = n5 + cl.a(cj, a, n2 + 1, c, true)) >= 1000000.0) {
            return;
        }
        final aow u;
        if ((u = cj.a(n, n2 - 1, n3).u()) == aox.au || u == aox.bn) {
            return;
        }
        final awt a3 = cj.a(a, n2 - 2, c);
        if (!cl.c(cj.a, a, n2 - 2, c, a3)) {
            a(cj, n2, a, c, n6, a3, gj);
            return;
        }
        if (a2.u() == aox.au || a2.u() == aox.bn) {
            return;
        }
        double n7 = 3.7062775075283763;
        if (u == aox.aW) {
            n7 = 7.4125550150567525;
        }
        final double a4 = n6 + (n7 + Math.max(dn.FALL_N_BLOCKS_COST[1], 0.9265693768820937));
        gj.a = a;
        gj.b = n2 - 1;
        gj.c = c;
        gj.a = a4;
    }
    
    public static boolean a(final cj cj, final int n, final int a, final int c, final double n2, final awt awt, final gj gj) {
        if (n2 != 0.0 && cj.a(a, n + 2, c).u() instanceof aqm) {
            return false;
        }
        if (!cl.b(cj.a, a, n - 2, c, awt)) {
            return false;
        }
        double n3 = 0.0;
        int n4 = n;
        int n5 = 3;
        int b;
        while ((b = n - n5) >= 0) {
            final awt a2 = cj.a(a, b, c);
            final int n6 = n5 - (n - n4);
            final double n7 = 3.7062775075283763 + dn.FALL_N_BLOCKS_COST[n6] + n2 + n3;
            if (cl.b(a2.u())) {
                if (!cl.b(cj.a, a, b, c, a2)) {
                    return false;
                }
                if (cj.j) {
                    return false;
                }
                if (cl.a(a, b, c, a2, cj.a)) {
                    return false;
                }
                if (!cl.b(cj.a, a, b - 1, c)) {
                    return false;
                }
                gj.a = a;
                gj.b = b;
                gj.c = c;
                gj.a = n7;
                return false;
            }
            else {
                if (n6 <= 11 && (a2.u() == aox.bn || a2.u() == aox.au)) {
                    n3 = n3 + dn.FALL_N_BLOCKS_COST[n6 - 1] + 6.666666666666667;
                    n4 = b;
                }
                else if (!cl.b(cj.a, a, b, c, a2)) {
                    if (!cl.c(cj.a, a, b, c, a2)) {
                        return false;
                    }
                    if (cl.a(a2)) {
                        return false;
                    }
                    if (n6 <= cj.a + 1) {
                        gj.a = a;
                        gj.b = b + 1;
                        gj.c = c;
                        gj.a = n7;
                        return false;
                    }
                    if (cj.b && n6 <= cj.b + 1) {
                        gj.a = a;
                        gj.b = b + 1;
                        gj.c = c;
                        gj.a = n7 + cj.a();
                        return true;
                    }
                    return false;
                }
                ++n5;
            }
        }
        return false;
    }
    
    @Override
    public final cn a(final cn cn) {
        super.a(cn);
        if (cn.a != MovementStatus.RUNNING) {
            return cn;
        }
        final BetterBlockPos playerFeet = this.a.playerFeet();
        final et et = new et((this.b.p() << 1) - this.a.p(), this.b.q(), (this.b.r() << 1) - this.a.r());
        if ((playerFeet.equals((Object)this.b) || playerFeet.equals((Object)et)) && (cl.e(this.a, this.b) || this.a.player().q - this.b.q() < 0.5)) {
            cn.a = MovementStatus.SUCCESS;
            return cn;
        }
        if (this.b()) {
            final double n = (this.a.p() + 0.5) * 0.17 + (this.b.p() + 0.5) * 0.83;
            final double n2 = (this.a.r() + 0.5) * 0.17 + (this.b.r() + 0.5) * 0.83;
            final bud player = this.a.player();
            cn.a(new co(new Rotation(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), new bhe(n, (double)this.b.q(), n2), new Rotation(player.v, player.w)).getYaw(), player.w), false)).a(Input.MOVE_FORWARD, true);
            return cn;
        }
        final double n3 = this.a.player().p - (this.b.p() + 0.5);
        final double n4 = this.a.player().r - (this.b.r() + 0.5);
        final double n5 = n3;
        final double n6 = n5 * n5;
        final double n7 = n4;
        final double sqrt = Math.sqrt(n6 + n7 * n7);
        final double n8 = this.a.player().p - (this.a.p() + 0.5);
        final double n9 = this.a.player().r - (this.a.r() + 0.5);
        final double n10 = n8;
        final double n11 = n10 * n10;
        final double n12 = n9;
        final double sqrt2 = Math.sqrt(n11 + n12 * n12);
        if (!playerFeet.equals((Object)this.b) || sqrt > 0.25) {
            if (this.a++ < 20 && sqrt2 < 1.25) {
                cl.a(this.a, cn, et);
            }
            else {
                cl.a(this.a, cn, this.b);
            }
        }
        return cn;
    }
    
    public final boolean b() {
        final et a = this.b.b((fq)this.a.down()).a((fq)this.b);
        if (this.c()) {
            return true;
        }
        for (int i = 0; i <= 2; ++i) {
            if (cl.a(fn.a(this.a, a.b(i)))) {
                return true;
            }
        }
        return false;
    }
    
    public final boolean c() {
        final et a = this.b.b((fq)this.a.down()).a((fq)this.b);
        return !cl.a(this.a, new BetterBlockPos(a)) && cl.a(this.a, new BetterBlockPos(a).up()) && cl.a(this.a, new BetterBlockPos(a).up(2));
    }
}
