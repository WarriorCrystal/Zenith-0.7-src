// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.ArrayList;
import java.util.List;
import baritone.api.utils.input.Input;
import baritone.api.pathing.movement.MovementStatus;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import baritone.api.utils.BetterBlockPos;
import baritone.api.IBaritone;

public final class do extends ck
{
    private static final double a;
    
    public do(final IBaritone baritone, final BetterBlockPos betterBlockPos, final fa fa, final fa fa2, final int n) {
        this(baritone, betterBlockPos, betterBlockPos.offset(fa), betterBlockPos.offset(fa2), fa2, n);
    }
    
    private do(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2, final BetterBlockPos betterBlockPos3, final fa fa, final int n) {
        this(baritone, betterBlockPos, betterBlockPos2.offset(fa).up(n), betterBlockPos2, betterBlockPos3);
    }
    
    private do(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2, final BetterBlockPos betterBlockPos3, final BetterBlockPos betterBlockPos4) {
        super(baritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[] { betterBlockPos3, betterBlockPos3.up(), betterBlockPos4, betterBlockPos4.up(), betterBlockPos2, betterBlockPos2.up() });
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
        final BetterBlockPos betterBlockPos = new BetterBlockPos(this.a.a, this.a.b, this.b.c);
        final BetterBlockPos betterBlockPos2 = new BetterBlockPos(this.b.a, this.a.b, this.a.c);
        if (this.b.b < this.a.b) {
            return (Set<BetterBlockPos>)ImmutableSet.of((Object)this.a, (Object)this.b.up(), (Object)betterBlockPos, (Object)betterBlockPos2, (Object)this.b, (Object)betterBlockPos.down(), (Object[])new BetterBlockPos[] { betterBlockPos2.down() });
        }
        if (this.b.b > this.a.b) {
            return (Set<BetterBlockPos>)ImmutableSet.of((Object)this.a, (Object)this.a.up(), (Object)betterBlockPos, (Object)betterBlockPos2, (Object)this.b, (Object)betterBlockPos.up(), (Object[])new BetterBlockPos[] { betterBlockPos2.up() });
        }
        return (Set<BetterBlockPos>)ImmutableSet.of((Object)this.a, (Object)this.b, (Object)betterBlockPos, (Object)betterBlockPos2);
    }
    
    public static void a(final cj cj, final int n, final int b, final int n2, final int n3, final int n4, final gj gj) {
        if (!cl.a(cj.a, n3, b + 1, n4)) {
            return;
        }
        final awt a = cj.a(n3, b, n4);
        boolean b2 = false;
        boolean b3 = false;
        awt a2;
        if (!cl.b(cj.a, n3, b, n4, a)) {
            b2 = true;
            if (!cj.l || !cl.a(cj.a, n, b + 2, n2) || !cl.c(cj.a, n3, b, n4, a) || !cl.a(cj.a, n3, b + 2, n4)) {
                return;
            }
            a2 = a;
        }
        else {
            a2 = cj.a(n3, b - 1, n4);
            if (!cl.c(cj.a, n3, b - 1, n4, a2)) {
                b3 = true;
                if (!cj.k || !cl.b(cj.a, n3, b - 2, n4) || !cl.b(cj.a, n3, b - 1, n4, a2)) {
                    return;
                }
            }
        }
        double b4 = 4.63284688441047;
        if (a2.u() == aox.aW) {
            b4 = 6.949270326615705;
        }
        else if (a2.u() == aox.j) {
            b4 = 4.63284688441047 + cj.f * do.a;
        }
        final aow u;
        if ((u = cj.a(n, b - 1, n2).u()) == aox.au || u == aox.bn) {
            return;
        }
        if (u == aox.aW) {
            b4 += 2.316423442205235;
        }
        final aow u2;
        if ((u2 = cj.a(n, b - 1, n4).u()) == aox.df || cl.c(u2)) {
            return;
        }
        final aow u3;
        if ((u3 = cj.a(n3, b - 1, n2).u()) == aox.df || cl.c(u3)) {
            return;
        }
        final aow a3 = cj.a(n, b, n2);
        boolean b5 = false;
        if (cl.b(a3) || cl.b(a.u())) {
            if (b2) {
                return;
            }
            b4 = cj.b;
            b5 = true;
        }
        final awt a4 = cj.a(n, b, n4);
        final awt a5 = cj.a(n3, b, n2);
        if (b2) {
            final boolean a6 = cl.a(cj.a, n, b + 2, n4);
            final boolean a7 = cl.a(cj.a, n, b + 1, n4);
            final boolean b6 = cl.b(cj.a, n, b, n4, a4);
            final boolean a8 = cl.a(cj.a, n3, b + 2, n2);
            final boolean a9 = cl.a(cj.a, n3, b + 1, n2);
            final boolean b7 = cl.b(cj.a, n3, b, n2, a5);
            if (((!a6 || !a7 || !b6) && (!a8 || !a9 || !b7)) || cl.a(a4.u()) || cl.a(a5.u()) || (a6 && a7 && cl.c(cj.a, n, b, n4, a4)) || (a8 && a9 && cl.c(cj.a, n3, b, n2, a5)) || (!a6 && a7 && b6) || (!a8 && a9 && b7)) {
                return;
            }
            gj.a = b4 * do.a + do.JUMP_ONE_BLOCK_COST;
            gj.a = n3;
            gj.c = n4;
            gj.b = b + 1;
        }
        else {
            final double a10 = cl.a(cj, n, b, n4, a4, false);
            final double a11 = cl.a(cj, n3, b, n2, a5, false);
            if (a10 != 0.0 && a11 != 0.0) {
                return;
            }
            final awt a12 = cj.a(n, b + 1, n4);
            final double n5;
            if ((n5 = a10 + cl.a(cj, n, b + 1, n4, a12, true)) != 0.0 && a11 != 0.0) {
                return;
            }
            final awt a13 = cj.a(n3, b + 1, n2);
            if (n5 == 0.0 && ((cl.a(a5.u()) && a5.u() != aox.j) || cl.a(a13.u()))) {
                return;
            }
            final double n6 = a11 + cl.a(cj, n3, b + 1, n2, a13, true);
            if (n5 != 0.0 && n6 != 0.0) {
                return;
            }
            if (n6 == 0.0 && ((cl.a(a4.u()) && a4.u() != aox.j) || cl.a(a12.u()))) {
                return;
            }
            if (n5 != 0.0 || n6 != 0.0) {
                b4 *= do.a - 0.001;
                if (a3 == aox.au || a3 == aox.bn) {
                    return;
                }
            }
            else if (cj.d && !b5) {
                b4 *= 0.7692444761225944;
            }
            gj.a = b4 * do.a;
            if (b3) {
                gj.a += Math.max(do.FALL_N_BLOCKS_COST[1], 0.9265693768820937);
                gj.b = b - 1;
            }
            else {
                gj.b = b;
            }
            gj.a = n3;
            gj.c = n4;
        }
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
        if (!this.a() && (!cl.e(this.a, this.a) || !this.b().contains(this.a.playerFeet().up()))) {
            cn.a = MovementStatus.UNREACHABLE;
            return cn;
        }
        if (this.b.b > this.a.b && this.a.player().q < this.a.b + 0.1 && this.a.player().A) {
            cn.a(Input.JUMP, true);
        }
        boolean b = false;
        Label_0254: {
            if (cl.e(this.a, this.a.playerFeet()) && !baritone.a.a().sprintInWater.value) {
                b = false;
            }
            else {
                for (int i = 0; i < 4; ++i) {
                    if (!cl.a(this.a, this.a[i])) {
                        b = false;
                        break Label_0254;
                    }
                }
                b = true;
            }
        }
        if (b) {
            cn.a(Input.SPRINT, true);
        }
        cl.a(this.a, cn, this.b);
        return cn;
    }
    
    public final boolean a(final cn cn) {
        return true;
    }
    
    @Override
    public final List<et> a(final fn fn) {
        if (this.a != null) {
            return this.a;
        }
        final ArrayList<BetterBlockPos> a = new ArrayList<BetterBlockPos>();
        for (int i = 4; i < 6; ++i) {
            if (!cl.a(fn, this.a[i].a, this.a[i].b, this.a[i].c)) {
                a.add(this.a[i]);
            }
        }
        return this.a = (List<et>)a;
    }
    
    @Override
    public final List<et> c(final fn fn) {
        if (this.b == null) {
            this.b = new ArrayList<et>();
        }
        final ArrayList<BetterBlockPos> b = new ArrayList<BetterBlockPos>();
        for (int i = 0; i < 4; ++i) {
            if (!cl.a(fn, this.a[i].a, this.a[i].b, this.a[i].c)) {
                b.add(this.a[i]);
            }
        }
        return this.b = (List<et>)b;
    }
    
    static {
        a = Math.sqrt(2.0);
    }
}
