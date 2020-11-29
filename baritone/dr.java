// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.utils.input.Input;
import baritone.api.pathing.movement.MovementStatus;
import java.util.HashSet;
import java.util.Set;
import baritone.api.IBaritone;
import baritone.api.utils.BetterBlockPos;

public final class dr extends ck
{
    private static final BetterBlockPos[] b;
    private final fa a;
    private final int a;
    private final boolean a;
    
    private dr(final IBaritone baritone, final BetterBlockPos betterBlockPos, final int a, final fa a2, final boolean a3) {
        super(baritone, betterBlockPos, betterBlockPos.offset(a2, a).up((int)(a3 ? 1 : 0)), dr.b, betterBlockPos.offset(a2, a).down((int)(a3 ? 0 : 1)));
        this.a = a2;
        this.a = a;
        this.a = a3;
    }
    
    public static dr a(final cj cj, final BetterBlockPos betterBlockPos, final fa fa) {
        final gj gj = new gj();
        a(cj, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, fa, gj);
        return new dr(cj.a, betterBlockPos, Math.abs(gj.a - betterBlockPos.a) + Math.abs(gj.c - betterBlockPos.c), fa, gj.b > betterBlockPos.b);
    }
    
    public static void a(final cj cj, final int n, final int n2, final int n3, final fa fa, final gj gj) {
        if (!cj.f) {
            return;
        }
        if (n2 == 256 && !cj.h) {
            return;
        }
        final int g = fa.g();
        final int i = fa.i();
        if (!cl.a(cj, n + g, n2, n3 + i)) {
            return;
        }
        final awt a = cj.a(n + g, n2 - 1, n3 + i);
        if (cl.c(cj.a, n + g, n2 - 1, n3 + i, a)) {
            return;
        }
        if (cl.a(a.u()) && a.u() != aox.j && a.u() != aox.i) {
            return;
        }
        if (!cl.a(cj, n + g, n2 + 1, n3 + i)) {
            return;
        }
        if (!cl.a(cj, n + g, n2 + 2, n3 + i)) {
            return;
        }
        if (!cl.a(cj, n, n2 + 2, n3)) {
            return;
        }
        final awt a2;
        if ((a2 = cj.a(n, n2 - 1, n3)).u() == aox.bn || a2.u() == aox.au || a2.u() instanceof aud || cl.a(a2) || a2.u() instanceof aru) {
            return;
        }
        int n4;
        if (a2.u() == aox.aW) {
            n4 = 2;
        }
        else if (cj.d) {
            n4 = 4;
        }
        else {
            n4 = 3;
        }
        for (int j = 2; j <= n4; ++j) {
            final int n5 = n + g * j;
            final int n6 = n3 + i * j;
            if (!cl.a(cj, n5, n2 + 1, n6)) {
                return;
            }
            if (!cl.a(cj, n5, n2 + 2, n6)) {
                return;
            }
            final awt a3 = cj.a.a(n5, n2, n6);
            if (!cl.a(cj.a.b, (et)cj.a.a.c(n5, n2, n6), a3)) {
                if (j <= 3 && cj.i && cj.d && cl.c(cj.a, n5, n2, n6, a3) && d(cj.a, n5 + g, n2 + 1, n6 + i)) {
                    gj.a = n5;
                    gj.b = n2 + 1;
                    gj.c = n6;
                    gj.a = j * 3.563791874554526 + cj.e;
                }
                return;
            }
            final awt a4;
            if ((a4 = cj.a.a(n5, n2 - 1, n6)).u() != aox.ak && cl.c(cj.a, n5, n2 - 1, n6, a4)) {
                if (d(cj.a, n5 + g, n2, n6 + i)) {
                    gj.a = n5;
                    gj.b = n2;
                    gj.c = n6;
                    gj.a = a(j) + cj.e;
                }
                return;
            }
            if (!cl.a(cj, n5, n2 + 3, n6)) {
                return;
            }
        }
        if (n4 != 4) {
            return;
        }
        if (!cj.g) {
            return;
        }
        final int a5 = n + 4 * g;
        final int c = n3 + 4 * i;
        final awt a6 = cj.a(a5, n2 - 1, c);
        final double a7;
        if ((a7 = cj.a(a5, n2 - 1, c, a6)) >= 1000000.0) {
            return;
        }
        if (!cl.a(a5, c, a6, cj.a)) {
            return;
        }
        if (!d(cj.a, a5 + g, n2, c + i)) {
            return;
        }
        for (int k = 0; k < 5; ++k) {
            final int n7 = a5 + dr.a[k].g();
            final int n8 = n2 - 1 + dr.a[k].h();
            final int n9 = c + dr.a[k].i();
            if ((n7 != n + g * 3 || n9 != n3 + i * 3) && cl.c(cj.a, n7, n8, n9)) {
                gj.a = a5;
                gj.b = n2;
                gj.c = c;
                gj.a = a(4) + a7 + cj.e;
                return;
            }
        }
    }
    
    private static boolean d(final fn fn, final int n, final int n2, final int n3) {
        return !cl.a(fn.a(n, n2, n3).u()) && !cl.a(fn.a(n, n2 + 1, n3).u());
    }
    
    private static double a(final int i) {
        switch (i) {
            case 2: {
                return 9.26569376882094;
            }
            case 3: {
                return 13.89854065323141;
            }
            case 4: {
                return 14.255167498218103;
            }
            default: {
                throw new IllegalStateException("LOL ".concat(String.valueOf(i)));
            }
        }
    }
    
    @Override
    public final double a(final cj cj) {
        final gj gj = new gj();
        a(cj, this.a.a, this.a.b, this.a.c, this.a, gj);
        if (gj.a != this.b.a || gj.b != this.b.b || gj.c != this.b.c) {
            return 1000000.0;
        }
        return gj.a;
    }
    
    public final Set<BetterBlockPos> a() {
        final HashSet<BetterBlockPos> set = new HashSet<BetterBlockPos>();
        for (int i = 0; i <= this.a; ++i) {
            for (int j = 0; j < 2; ++j) {
                set.add(this.a.offset(this.a, i).up(j));
            }
        }
        return set;
    }
    
    public final boolean b(final cn cn) {
        return cn.a != MovementStatus.RUNNING;
    }
    
    @Override
    public final cn a(final cn cn) {
        super.a(cn);
        if (cn.a != MovementStatus.RUNNING) {
            return cn;
        }
        if (this.a.playerFeet().b < this.a.b) {
            this.logDebug("sorry");
            cn.a = MovementStatus.UNREACHABLE;
            return cn;
        }
        if (this.a >= 4 || this.a) {
            cn.a(Input.SPRINT, true);
        }
        cl.a(this.a, cn, this.b);
        if (this.a.playerFeet().equals(this.b)) {
            final aow a;
            if ((a = fn.a(this.a, (et)this.b)) == aox.bn || a == aox.au) {
                cn.a = MovementStatus.SUCCESS;
                return cn;
            }
            if (this.a.player().q - this.a.playerFeet().q() < 0.094) {
                cn.a = MovementStatus.SUCCESS;
            }
        }
        else if (!this.a.playerFeet().equals(this.a)) {
            if (this.a.playerFeet().equals(this.a.offset(this.a)) || this.a.player().q - this.a.b > 1.0E-4) {
                if (!cl.b(this.a, this.b.down()) && !this.a.player().z && cl.a(cn, this.a, this.b.down(), true, false) == cm.a) {
                    cn.a(Input.CLICK_RIGHT, true);
                }
                if (this.a == 3 && !this.a && Math.max(Math.abs(this.a.a + 0.5 - this.a.player().p), Math.abs(this.a.c + 0.5 - this.a.player().r)) < 0.7) {
                    return cn;
                }
                cn.a(Input.JUMP, true);
            }
            else if (!this.a.playerFeet().equals(this.b.offset(this.a, -1))) {
                cn.a(Input.SPRINT, false);
                if (this.a.playerFeet().equals(this.a.offset(this.a, -1))) {
                    cl.a(this.a, cn, this.a);
                }
                else {
                    cl.a(this.a, cn, this.a.offset(this.a, -1));
                }
            }
        }
        return cn;
    }
    
    static {
        b = new BetterBlockPos[0];
    }
}
