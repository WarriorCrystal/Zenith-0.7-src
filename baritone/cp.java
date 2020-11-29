// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.utils.BetterBlockPos;

public enum cp
{
    a("DOWNWARD") {
        cq(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            return new dp(cj.a, betterBlockPos, betterBlockPos.down());
        }
        
        @Override
        public final double a(final cj cj, final int n, final int n2, final int n3) {
            return dp.a(cj, n, n2, n3);
        }
    }, 
    b("PILLAR") {
        db(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            return new ds(cj.a, betterBlockPos, betterBlockPos.up());
        }
        
        @Override
        public final double a(final cj cj, final int n, final int n2, final int n3) {
            return ds.a(cj, n, n2, n3);
        }
    }, 
    c("TRAVERSE_NORTH") {
        df(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            return new dt(cj.a, betterBlockPos, betterBlockPos.north());
        }
        
        @Override
        public final double a(final cj cj, final int n, final int n2, final int n3) {
            return dt.a(cj, n, n2, n3, n, n3 - 1);
        }
    }, 
    d("TRAVERSE_SOUTH") {
        dg(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            return new dt(cj.a, betterBlockPos, betterBlockPos.south());
        }
        
        @Override
        public final double a(final cj cj, final int n, final int n2, final int n3) {
            return dt.a(cj, n, n2, n3, n, n3 + 1);
        }
    }, 
    e("TRAVERSE_EAST") {
        dh(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            return new dt(cj.a, betterBlockPos, betterBlockPos.east());
        }
        
        @Override
        public final double a(final cj cj, final int n, final int n2, final int n3) {
            return dt.a(cj, n, n2, n3, n + 1, n3);
        }
    }, 
    f("TRAVERSE_WEST") {
        di(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            return new dt(cj.a, betterBlockPos, betterBlockPos.west());
        }
        
        @Override
        public final double a(final cj cj, final int n, final int n2, final int n3) {
            return dt.a(cj, n, n2, n3, n - 1, n3);
        }
    }, 
    g("ASCEND_NORTH") {
        dj(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            return new dm(cj.a, betterBlockPos, new BetterBlockPos(betterBlockPos.a, betterBlockPos.b + 1, betterBlockPos.c - 1));
        }
        
        @Override
        public final double a(final cj cj, final int n, final int n2, final int n3) {
            return dm.a(cj, n, n2, n3, n, n3 - 1);
        }
    }, 
    h("ASCEND_SOUTH") {
        dk(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            return new dm(cj.a, betterBlockPos, new BetterBlockPos(betterBlockPos.a, betterBlockPos.b + 1, betterBlockPos.c + 1));
        }
        
        @Override
        public final double a(final cj cj, final int n, final int n2, final int n3) {
            return dm.a(cj, n, n2, n3, n, n3 + 1);
        }
    }, 
    i("ASCEND_EAST") {
        dl(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            return new dm(cj.a, betterBlockPos, new BetterBlockPos(betterBlockPos.a + 1, betterBlockPos.b + 1, betterBlockPos.c));
        }
        
        @Override
        public final double a(final cj cj, final int n, final int n2, final int n3) {
            return dm.a(cj, n, n2, n3, n + 1, n3);
        }
    }, 
    j("ASCEND_WEST") {
        cr(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            return new dm(cj.a, betterBlockPos, new BetterBlockPos(betterBlockPos.a - 1, betterBlockPos.b + 1, betterBlockPos.c));
        }
        
        @Override
        public final double a(final cj cj, final int n, final int n2, final int n3) {
            return dm.a(cj, n, n2, n3, n - 1, n3);
        }
    }, 
    k("DESCEND_EAST") {
        cs(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            final gj gj = new gj();
            this.a(cj, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, gj);
            if (gj.b == betterBlockPos.b - 1) {
                return new dn(cj.a, betterBlockPos, new BetterBlockPos(gj.a, gj.b, gj.c));
            }
            return new dq(cj.a, betterBlockPos, new BetterBlockPos(gj.a, gj.b, gj.c));
        }
        
        @Override
        public final void a(final cj cj, final int n, final int n2, final int n3, final gj gj) {
            dn.a(cj, n, n2, n3, n + 1, n3, gj);
        }
    }, 
    l("DESCEND_WEST") {
        ct(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            final gj gj = new gj();
            this.a(cj, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, gj);
            if (gj.b == betterBlockPos.b - 1) {
                return new dn(cj.a, betterBlockPos, new BetterBlockPos(gj.a, gj.b, gj.c));
            }
            return new dq(cj.a, betterBlockPos, new BetterBlockPos(gj.a, gj.b, gj.c));
        }
        
        @Override
        public final void a(final cj cj, final int n, final int n2, final int n3, final gj gj) {
            dn.a(cj, n, n2, n3, n - 1, n3, gj);
        }
    }, 
    m("DESCEND_NORTH") {
        cu(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            final gj gj = new gj();
            this.a(cj, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, gj);
            if (gj.b == betterBlockPos.b - 1) {
                return new dn(cj.a, betterBlockPos, new BetterBlockPos(gj.a, gj.b, gj.c));
            }
            return new dq(cj.a, betterBlockPos, new BetterBlockPos(gj.a, gj.b, gj.c));
        }
        
        @Override
        public final void a(final cj cj, final int n, final int n2, final int n3, final gj gj) {
            dn.a(cj, n, n2, n3, n, n3 - 1, gj);
        }
    }, 
    n("DESCEND_SOUTH") {
        cv(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            final gj gj = new gj();
            this.a(cj, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, gj);
            if (gj.b == betterBlockPos.b - 1) {
                return new dn(cj.a, betterBlockPos, new BetterBlockPos(gj.a, gj.b, gj.c));
            }
            return new dq(cj.a, betterBlockPos, new BetterBlockPos(gj.a, gj.b, gj.c));
        }
        
        @Override
        public final void a(final cj cj, final int n, final int n2, final int n3, final gj gj) {
            dn.a(cj, n, n2, n3, n, n3 + 1, gj);
        }
    }, 
    o("DIAGONAL_NORTHEAST") {
        cw(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            final gj gj = new gj();
            this.a(cj, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, gj);
            return new do(cj.a, betterBlockPos, fa.c, fa.f, gj.b - betterBlockPos.b);
        }
        
        @Override
        public final void a(final cj cj, final int n, final int n2, final int n3, final gj gj) {
            do.a(cj, n, n2, n3, n + 1, n3 - 1, gj);
        }
    }, 
    p("DIAGONAL_NORTHWEST") {
        cx(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            final gj gj = new gj();
            this.a(cj, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, gj);
            return new do(cj.a, betterBlockPos, fa.c, fa.e, gj.b - betterBlockPos.b);
        }
        
        @Override
        public final void a(final cj cj, final int n, final int n2, final int n3, final gj gj) {
            do.a(cj, n, n2, n3, n - 1, n3 - 1, gj);
        }
    }, 
    q("DIAGONAL_SOUTHEAST") {
        cy(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            final gj gj = new gj();
            this.a(cj, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, gj);
            return new do(cj.a, betterBlockPos, fa.d, fa.f, gj.b - betterBlockPos.b);
        }
        
        @Override
        public final void a(final cj cj, final int n, final int n2, final int n3, final gj gj) {
            do.a(cj, n, n2, n3, n + 1, n3 + 1, gj);
        }
    }, 
    r("DIAGONAL_SOUTHWEST") {
        cz(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            final gj gj = new gj();
            this.a(cj, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, gj);
            return new do(cj.a, betterBlockPos, fa.d, fa.e, gj.b - betterBlockPos.b);
        }
        
        @Override
        public final void a(final cj cj, final int n, final int n2, final int n3, final gj gj) {
            do.a(cj, n, n2, n3, n - 1, n3 + 1, gj);
        }
    }, 
    s("PARKOUR_NORTH") {
        da(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            return dr.a(cj, betterBlockPos, fa.c);
        }
        
        @Override
        public final void a(final cj cj, final int n, final int n2, final int n3, final gj gj) {
            dr.a(cj, n, n2, n3, fa.c, gj);
        }
    }, 
    t("PARKOUR_SOUTH") {
        dc(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            return dr.a(cj, betterBlockPos, fa.d);
        }
        
        @Override
        public final void a(final cj cj, final int n, final int n2, final int n3, final gj gj) {
            dr.a(cj, n, n2, n3, fa.d, gj);
        }
    }, 
    u("PARKOUR_EAST") {
        dd(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            return dr.a(cj, betterBlockPos, fa.f);
        }
        
        @Override
        public final void a(final cj cj, final int n, final int n2, final int n3, final gj gj) {
            dr.a(cj, n, n2, n3, fa.f, gj);
        }
    }, 
    v("PARKOUR_WEST") {
        de(final String s) {
        }
        
        @Override
        public final ck a(final cj cj, final BetterBlockPos betterBlockPos) {
            return dr.a(cj, betterBlockPos, fa.e);
        }
        
        @Override
        public final void a(final cj cj, final int n, final int n2, final int n3, final gj gj) {
            dr.a(cj, n, n2, n3, fa.e, gj);
        }
    };
    
    public final boolean a;
    public final boolean b;
    public final int a;
    public final int b;
    public final int c;
    
    private cp(final int a, final int b, final int c, final boolean a2, final boolean b2) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.a = a2;
        this.b = b2;
    }
    
    private cp(final int n2, final int n3, final int n4) {
        this(n2, n3, n4, false, false);
    }
    
    public abstract ck a(final cj p0, final BetterBlockPos p1);
    
    public void a(final cj cj, final int n, final int n2, final int n3, final gj gj) {
        if (this.a || this.b) {
            throw new UnsupportedOperationException();
        }
        gj.a = n + this.a;
        gj.b = n2 + this.b;
        gj.c = n3 + this.c;
        gj.a = this.a(cj, n, n2, n3);
    }
    
    public double a(final cj cj, final int n, final int n2, final int n3) {
        throw new UnsupportedOperationException();
    }
}
