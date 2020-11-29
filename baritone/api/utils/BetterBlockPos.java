// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

import javax.annotation.Nonnull;

public final class BetterBlockPos extends et
{
    public static final BetterBlockPos ORIGIN;
    public final int a;
    public final int b;
    public final int c;
    
    public BetterBlockPos(final int a, final int b, final int c) {
        super(a, b, c);
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public BetterBlockPos(final double n, final double n2, final double n3) {
        this(rk.c(n), rk.c(n2), rk.c(n3));
    }
    
    public BetterBlockPos(final et et) {
        this(et.p(), et.q(), et.r());
    }
    
    public static BetterBlockPos from(final et et) {
        if (et == null) {
            return null;
        }
        return new BetterBlockPos(et);
    }
    
    public final int hashCode() {
        return (int)longHash(this.a, this.b, this.c);
    }
    
    public static long longHash(final BetterBlockPos betterBlockPos) {
        return longHash(betterBlockPos.a, betterBlockPos.b, betterBlockPos.c);
    }
    
    public static long longHash(final int n, final int n2, final int n3) {
        return 2873465L * (8734625L * (11206370049L + n) + n2) + n3;
    }
    
    public final boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof BetterBlockPos) {
            final BetterBlockPos betterBlockPos;
            return (betterBlockPos = (BetterBlockPos)o).a == this.a && betterBlockPos.b == this.b && betterBlockPos.c == this.c;
        }
        final et et;
        return (et = (et)o).p() == this.a && et.q() == this.b && et.r() == this.c;
    }
    
    public final BetterBlockPos up() {
        return new BetterBlockPos(this.a, this.b + 1, this.c);
    }
    
    public final BetterBlockPos up(final int n) {
        if (n == 0) {
            return this;
        }
        return new BetterBlockPos(this.a, this.b + n, this.c);
    }
    
    public final BetterBlockPos down() {
        return new BetterBlockPos(this.a, this.b - 1, this.c);
    }
    
    public final BetterBlockPos down(final int n) {
        if (n == 0) {
            return this;
        }
        return new BetterBlockPos(this.a, this.b - n, this.c);
    }
    
    public final BetterBlockPos offset(final fa fa) {
        final fq n = fa.n();
        return new BetterBlockPos(this.a + n.p(), this.b + n.q(), this.c + n.r());
    }
    
    public final BetterBlockPos offset(final fa fa, final int n) {
        if (n == 0) {
            return this;
        }
        final fq n2 = fa.n();
        return new BetterBlockPos(this.a + n2.p() * n, this.b + n2.q() * n, this.c + n2.r() * n);
    }
    
    public final BetterBlockPos north() {
        return new BetterBlockPos(this.a, this.b, this.c - 1);
    }
    
    public final BetterBlockPos north(final int n) {
        if (n == 0) {
            return this;
        }
        return new BetterBlockPos(this.a, this.b, this.c - n);
    }
    
    public final BetterBlockPos south() {
        return new BetterBlockPos(this.a, this.b, this.c + 1);
    }
    
    public final BetterBlockPos south(final int n) {
        if (n == 0) {
            return this;
        }
        return new BetterBlockPos(this.a, this.b, this.c + n);
    }
    
    public final BetterBlockPos east() {
        return new BetterBlockPos(this.a + 1, this.b, this.c);
    }
    
    public final BetterBlockPos east(final int n) {
        if (n == 0) {
            return this;
        }
        return new BetterBlockPos(this.a + n, this.b, this.c);
    }
    
    public final BetterBlockPos west() {
        return new BetterBlockPos(this.a - 1, this.b, this.c);
    }
    
    public final BetterBlockPos west(final int n) {
        if (n == 0) {
            return this;
        }
        return new BetterBlockPos(this.a - n, this.b, this.c);
    }
    
    @Nonnull
    public final String toString() {
        return String.format("BetterBlockPos{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(this.a), SettingsUtil.maybeCensor(this.b), SettingsUtil.maybeCensor(this.c));
    }
    
    static {
        ORIGIN = new BetterBlockPos(0, 0, 0);
    }
}
