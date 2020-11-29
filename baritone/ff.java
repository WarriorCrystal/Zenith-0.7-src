// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.utils.BetterBlockPos;
import baritone.api.selection.ISelection;

public final class ff implements ISelection
{
    private final BetterBlockPos a;
    private final BetterBlockPos b;
    private final BetterBlockPos c;
    private final BetterBlockPos d;
    private final fq a;
    private final bhb a;
    
    public ff(final BetterBlockPos a, final BetterBlockPos b) {
        this.a = a;
        this.b = b;
        this.c = new BetterBlockPos(Math.min(a.a, b.a), Math.min(a.b, b.b), Math.min(a.c, b.c));
        this.d = new BetterBlockPos(Math.max(a.a, b.a), Math.max(a.b, b.b), Math.max(a.c, b.c));
        this.a = new fq(this.d.a - this.c.a + 1, this.d.b - this.c.b + 1, this.d.c - this.c.c + 1);
        this.a = new bhb((et)this.c, this.d.a(1, 1, 1));
    }
    
    @Override
    public final BetterBlockPos pos1() {
        return this.a;
    }
    
    @Override
    public final BetterBlockPos pos2() {
        return this.b;
    }
    
    @Override
    public final BetterBlockPos min() {
        return this.c;
    }
    
    @Override
    public final BetterBlockPos max() {
        return this.d;
    }
    
    @Override
    public final fq size() {
        return this.a;
    }
    
    @Override
    public final bhb aabb() {
        return this.a;
    }
    
    @Override
    public final int hashCode() {
        return this.a.hashCode() ^ this.b.hashCode();
    }
    
    @Override
    public final String toString() {
        return String.format("Selection{pos1=%s,pos2=%s}", this.a, this.b);
    }
    
    private boolean a(final fa fa) {
        final boolean b = fa.c().a() < 0;
        switch (fg.a[fa.k().ordinal()]) {
            case 1: {
                return this.b.a > this.a.a ^ b;
            }
            case 2: {
                return this.b.b > this.a.b ^ b;
            }
            case 3: {
                return this.b.c > this.a.c ^ b;
            }
            default: {
                throw new IllegalStateException("Bad EnumFacing.Axis");
            }
        }
    }
    
    @Override
    public final ISelection expand(final fa fa, final int n) {
        if (this.a(fa)) {
            return new ff(this.a, this.b.offset(fa, n));
        }
        return new ff(this.a.offset(fa, n), this.b);
    }
    
    @Override
    public final ISelection contract(final fa fa, final int n) {
        if (this.a(fa)) {
            return new ff(this.a.offset(fa, n), this.b);
        }
        return new ff(this.a, this.b.offset(fa, n));
    }
    
    @Override
    public final ISelection shift(final fa fa, final int n) {
        return new ff(this.a.offset(fa, n), this.b.offset(fa, n));
    }
}
