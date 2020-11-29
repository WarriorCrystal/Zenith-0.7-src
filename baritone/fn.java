// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import baritone.api.utils.IPlayerContext;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;

public final class fn
{
    private final Long2ObjectMap<axw> a;
    private final u a;
    protected final amy a;
    public final et$a a;
    public final amy b;
    private axw a;
    private m a;
    private final boolean a;
    private static final awt a;
    
    public fn(final IPlayerContext playerContext) {
        this(playerContext, false);
    }
    
    public fn(final IPlayerContext playerContext, final boolean b) {
        this(playerContext.world(), (u)playerContext.worldData(), b);
    }
    
    public fn(final amu a, final u a2, final boolean b) {
        this.a = null;
        this.a = null;
        this.a = (amy)a;
        this.a = a2;
        final Long2ObjectMap<axw> loadedChunks = ((gd)a.B()).loadedChunks();
        if (b) {
            this.a = (Long2ObjectMap<axw>)new Long2ObjectOpenHashMap((Long2ObjectMap)loadedChunks);
        }
        else {
            this.a = loadedChunks;
        }
        this.a = !baritone.a.a().pathThroughCachedOnly.value;
        if (!bib.z().aF()) {
            throw new IllegalStateException();
        }
        this.a = new et$a();
        this.b = (amy)new fo(this);
    }
    
    public final boolean a(final int n, final int n2) {
        return this.a.containsKey(amn.a(n >> 4, n2 >> 4));
    }
    
    public static aow a(final IPlayerContext playerContext, final et et) {
        return a(playerContext, et).u();
    }
    
    public static awt a(final IPlayerContext playerContext, final et et) {
        return new fn(playerContext).a(et.p(), et.q(), et.r());
    }
    
    public final awt a(final et et) {
        return this.a(et.p(), et.q(), et.r());
    }
    
    public final awt a(final int n, final int n2, final int n3) {
        if (n2 < 0 || n2 >= 256) {
            return fn.a;
        }
        if (this.a) {
            final axw a;
            if ((a = this.a) != null && a.b == n >> 4 && a.c == n3 >> 4) {
                return a.a(n, n2, n3);
            }
            final axw a2;
            if ((a2 = (axw)this.a.get(amn.a(n >> 4, n3 >> 4))) != null && a2.p()) {
                this.a = a2;
                return a2.a(n, n2, n3);
            }
        }
        m a3;
        if ((a3 = this.a) == null || a3.getX() != n >> 9 || a3.getZ() != n3 >> 9) {
            if (this.a == null) {
                return fn.a;
            }
            if ((a3 = this.a.a.a(n >> 9, n3 >> 9)) == null) {
                return fn.a;
            }
            this.a = a3;
        }
        final awt block;
        if ((block = a3.getBlock(n & 0x1FF, n2, n3 & 0x1FF)) == null) {
            return fn.a;
        }
        return block;
    }
    
    public final boolean b(final int n, final int n2) {
        final axw a;
        if ((a = this.a) != null && a.b == n >> 4 && a.c == n2 >> 4) {
            return true;
        }
        final axw a2;
        if ((a2 = (axw)this.a.get(amn.a(n >> 4, n2 >> 4))) != null && a2.p()) {
            this.a = a2;
            return true;
        }
        final m a3;
        if ((a3 = this.a) != null && a3.getX() == n >> 9 && a3.getZ() == n2 >> 9) {
            return a3.isCached(n & 0x1FF, n2 & 0x1FF);
        }
        if (this.a == null) {
            return false;
        }
        final m a4;
        if ((a4 = this.a.a.a(n >> 9, n2 >> 9)) == null) {
            return false;
        }
        this.a = a4;
        return a4.isCached(n & 0x1FF, n2 & 0x1FF);
    }
    
    static {
        a = aox.a.t();
    }
}
