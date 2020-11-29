// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.BitSet;
import com.google.common.collect.ImmutableSet;

public final class l
{
    public static final ImmutableSet<aow> a;
    public final int a;
    public final int b;
    final BitSet a;
    final Int2ObjectOpenHashMap<String> a;
    final awt[] a;
    final int[] a;
    final Map<String, List<et>> a;
    public final long a;
    
    l(final int a, final int b, final BitSet a2, final awt[] a3, final Map<String, List<et>> a4, final long a5) {
        if (a2.size() > 131072) {
            throw new IllegalArgumentException("BitSet of invalid length provided");
        }
        this.a = a;
        this.b = b;
        this.a = a2;
        this.a = a3;
        this.a = new int[256];
        this.a = a4;
        this.a = a5;
        if (a4.isEmpty()) {
            this.a = null;
        }
        else {
            this.a = (Int2ObjectOpenHashMap<String>)new Int2ObjectOpenHashMap();
            this.a();
        }
        this.b();
    }
    
    private final void a() {
        final Iterator<Map.Entry<String, List<et>>> iterator = this.a.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<et>> entry;
            for (final et et : (entry = iterator.next()).getValue()) {
                this.a.put(a(et.p(), et.q(), et.r()), (Object)entry.getKey());
            }
        }
    }
    
    private void b() {
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                final int n = i << 4 | j;
                this.a[n] = 0;
                for (int k = 256; k >= 0; --k) {
                    final int a = a(j, k, i);
                    if (this.a.get(a) || this.a.get(a + 1)) {
                        this.a[n] = k;
                        break;
                    }
                }
            }
        }
    }
    
    public final ArrayList<et> a(final String s) {
        if (this.a.get(s) == null) {
            return null;
        }
        final ArrayList<et> list = new ArrayList<et>();
        for (final et et : this.a.get(s)) {
            list.add(new et(et.p() + (this.a << 4), et.q(), et.r() + (this.b << 4)));
        }
        return list;
    }
    
    public static int a(final int n, final int n2, final int n3) {
        return n << 1 | n3 << 5 | n2 << 9;
    }
    
    static {
        a = ImmutableSet.of((Object)aox.ah, (Object)aox.cA, (Object)aox.S, (Object)aox.R, (Object)aox.bP, (Object)aox.bT, (Object[])new aow[] { aox.bQ, aox.al, (aow)aox.ae, aox.cg, aox.bF, aox.bG, aox.ac, aox.cv, aox.dk, aox.dl, aox.dm, aox.dn, aox.do, aox.dp, aox.dq, aox.dr, aox.ds, aox.dt, aox.du, aox.dv, aox.dw, aox.dx, aox.dy, aox.dz, aox.dA, (aow)aox.aY, (aow)aox.cp, (aow)aox.bY, aox.bD, (aow)aox.ce, aox.bC, aox.cf, aox.am, aox.C, aox.bI, aox.aN, aox.db, aox.G, aox.bB, aox.au, aox.bn });
    }
}
