// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.cache.ICachedWorld;

final class eq implements es
{
    private final ICachedWorld a;
    private /* synthetic */ en a;
    
    private eq(final en a) {
        this.a = a;
        this.a = this.a.a.a.a.getCachedWorld();
    }
    
    @Override
    public final int a(int n, int n2) {
        n <<= 4;
        n2 <<= 4;
        if (this.a.isCached(n, n2)) {
            return eu.a;
        }
        if (((n)this.a).a(n >> 9, n2 >> 9) == null) {
            baritone.a.a().execute(() -> ((n)this.a).b(n >> 9, n2 >> 9));
            return eu.c;
        }
        return eu.b;
    }
    
    @Override
    public final int a() {
        return Integer.MAX_VALUE;
    }
}
