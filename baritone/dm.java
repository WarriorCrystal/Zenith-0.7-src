// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.utils.IPlayerContext;
import baritone.api.utils.input.Input;
import baritone.api.pathing.movement.MovementStatus;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import baritone.api.utils.BetterBlockPos;
import baritone.api.IBaritone;

public final class dm extends ck
{
    private int a;
    
    public dm(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        super(baritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[] { betterBlockPos2, betterBlockPos.up(2), betterBlockPos2.up() }, betterBlockPos2.down());
        this.a = 0;
    }
    
    @Override
    public final void reset() {
        super.reset();
        this.a = 0;
    }
    
    @Override
    public final double a(final cj cj) {
        return a(cj, this.a.a, this.a.b, this.a.c, this.b.a, this.b.c);
    }
    
    public final Set<BetterBlockPos> a() {
        final BetterBlockPos betterBlockPos = new BetterBlockPos(this.a.b((fq)this.getDirection()).a());
        final BetterBlockPos a = this.a;
        final BetterBlockPos up = this.a.up();
        final BetterBlockPos b = this.b;
        final BetterBlockPos betterBlockPos2 = betterBlockPos;
        return (Set<BetterBlockPos>)ImmutableSet.of((Object)a, (Object)up, (Object)b, (Object)betterBlockPos2, (Object)betterBlockPos2.up());
    }
    
    public static double a(final cj cj, final int n, final int n2, final int n3, final int n4, final int n5) {
        final awt a = cj.a(n4, n2, n5);
        double a2 = 0.0;
        if (!cl.c(cj.a, n4, n2, n5, a)) {
            if ((a2 = cj.a(n4, n2, n5, a)) >= 1000000.0) {
                return 1000000.0;
            }
            if (!cl.a(n4, n5, a, cj.a)) {
                return 1000000.0;
            }
            boolean b = false;
            for (int i = 0; i < 5; ++i) {
                final int n6 = n4 + dm.a[i].g();
                final int n7 = n2 + dm.a[i].h();
                final int n8 = n5 + dm.a[i].i();
                if ((n6 != n || n8 != n3) && cl.c(cj.a, n6, n7, n8)) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                return 1000000.0;
            }
        }
        final awt a3 = cj.a(n, n2 + 2, n3);
        if (cj.a(n, n2 + 3, n3).u() instanceof aqm && (cl.a(cj.a, n, n2 + 1, n3) || !(a3.u() instanceof aqm))) {
            return 1000000.0;
        }
        final awt a4;
        if ((a4 = cj.a(n, n2 - 1, n3)).u() == aox.au || a4.u() == aox.bn) {
            return 1000000.0;
        }
        final boolean a5 = cl.a(a4);
        final boolean a6 = cl.a(a);
        if (a5 && !a6) {
            return 1000000.0;
        }
        double n9 = 0.0;
        Label_0368: {
            double max = 0.0;
            Label_0359: {
                if (a6) {
                    if (!a5) {
                        n9 = 4.63284688441047;
                        break Label_0368;
                    }
                }
                else if (a.u() == aox.aW) {
                    max = 9.26569376882094;
                    break Label_0359;
                }
                max = Math.max(dm.JUMP_ONE_BLOCK_COST, 4.63284688441047);
            }
            n9 = max + cj.e;
        }
        final double n10;
        if ((n10 = n9 + a2 + cl.a(cj, n, n2 + 2, n3, a3, false)) >= 1000000.0) {
            return 1000000.0;
        }
        final double n11;
        if ((n11 = n10 + cl.a(cj, n4, n2 + 1, n5, false)) >= 1000000.0) {
            return 1000000.0;
        }
        return n11 + cl.a(cj, n4, n2 + 2, n5, true);
    }
    
    @Override
    public final cn a(final cn cn) {
        if (this.a.playerFeet().b < this.a.b) {
            cn.a = MovementStatus.UNREACHABLE;
            return cn;
        }
        super.a(cn);
        if (cn.a != MovementStatus.RUNNING) {
            return cn;
        }
        if (this.a.playerFeet().equals(this.b) || this.a.playerFeet().equals(this.b.a((fq)this.getDirection().b()))) {
            cn.a = MovementStatus.SUCCESS;
            return cn;
        }
        final awt a = fn.a(this.a, (et)this.c);
        final IPlayerContext a2 = this.a;
        final BetterBlockPos c = this.c;
        final awt awt = a;
        final BetterBlockPos betterBlockPos = c;
        if (!cl.c(new fn(a2), betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, awt)) {
            ++this.a;
            if (cl.a(cn, this.a, this.b.down(), false, true) == cm.a) {
                cn.a(Input.SNEAK, true);
                if (this.a.player().aU()) {
                    cn.a(Input.CLICK_RIGHT, true);
                }
            }
            if (this.a > 10) {
                cn.a(Input.MOVE_BACK, true);
            }
            return cn;
        }
        cl.a(this.a, cn, this.b);
        if (cl.a(a) && !cl.a(fn.a(this.a, (et)this.a.down()))) {
            return cn;
        }
        if (baritone.a.a().assumeStep.value || this.a.playerFeet().equals(this.a.up())) {
            return cn;
        }
        final int abs = Math.abs(this.a.p() - this.b.p());
        final int abs2 = Math.abs(this.a.r() - this.b.r());
        final double n = abs * Math.abs(this.b.p() + 0.5 - this.a.player().p) + abs2 * Math.abs(this.b.r() + 0.5 - this.a.player().r);
        final double n2 = abs2 * Math.abs(this.b.p() + 0.5 - this.a.player().p) + abs * Math.abs(this.b.r() + 0.5 - this.a.player().r);
        if (Math.abs(abs * this.a.player().u + abs2 * this.a.player().s) > 0.1) {
            return cn;
        }
        final BetterBlockPos up = this.a.up(2);
        int i = 0;
        while (true) {
            while (i < 4) {
                if (!cl.a(this.a, up.offset(fa.b(i)))) {
                    final boolean b = false;
                    if (b) {
                        return cn.a(Input.JUMP, true);
                    }
                    if (n > 1.2 || n2 > 0.2) {
                        return cn;
                    }
                    return cn.a(Input.JUMP, true);
                }
                else {
                    ++i;
                }
            }
            final boolean b = true;
            continue;
        }
    }
    
    public final boolean b(final cn cn) {
        return cn.a != MovementStatus.RUNNING || this.a == 0;
    }
}
