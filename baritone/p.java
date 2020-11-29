// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.Map;
import java.util.ArrayList;
import baritone.api.utils.BlockUtils;
import java.util.List;
import java.util.BitSet;
import java.util.HashMap;

public final class p
{
    private g[] a;
    public int a;
    
    public static l a(final axw axw) {
        final HashMap<String, Object> hashMap = new HashMap<String, Object>();
        final BitSet set = new BitSet(131072);
        try {
            final axx[] h = axw.h();
            for (int i = 0; i < 16; ++i) {
                final axx axx;
                if ((axx = h[i]) != null) {
                    final axp g = axx.g();
                    final int n = i << 4;
                    for (int j = 0; j < 16; ++j) {
                        final int n2 = j | n;
                        for (int k = 0; k < 16; ++k) {
                            for (int l = 0; l < 16; ++l) {
                                final int a = l.a(l, n2, k);
                                final awt a2;
                                final awt awt = a2 = g.a(l, j, k);
                                final int n3 = l;
                                final int n4 = n2;
                                final int n5 = k;
                                final int n6 = n4;
                                final int n7 = n3;
                                final awt awt2 = awt;
                                final aow u;
                                final boolean[] a3 = (((u = awt.u()) == aox.j || u == aox.i) ? (cl.b(awt2) ? gl.c : (((n7 != 15 && cl.b(axw.a(n7 + 1, n6, n5))) || (n7 != 0 && cl.b(axw.a(n7 - 1, n6, n5))) || (n5 != 15 && cl.b(axw.a(n7, n6, n5 + 1))) || (n5 != 0 && cl.b(axw.a(n7, n6, n5 - 1)))) ? gl.c : ((n7 == 0 || n7 == 15 || n5 == 0 || n5 == 15) ? ((aru.a((amy)axw.q(), new et(n7 + axw.b << 4, n6, n5 + axw.c << 4), awt2.a(), awt2) == -1000.0f) ? gl.b : gl.c) : gl.b))) : ((cl.a(u) || cl.a(awt2)) ? gl.c : ((u == aox.a || u instanceof aun || u instanceof aqb || u instanceof aqr) ? gl.a : gl.d))).a;
                                set.set(a, a3[0]);
                                set.set(a + 1, a3[1]);
                                final aow u2 = a2.u();
                                if (l.a.contains((Object)u2)) {
                                    hashMap.computeIfAbsent(BlockUtils.blockToString(u2), p0 -> new ArrayList()).add(new et(l, n2, k));
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        final awt[] array = new awt[256];
        for (int n8 = 0; n8 < 16; ++n8) {
            int n9 = 0;
        Label_0574:
            while (n9 < 16) {
                while (true) {
                    for (int n10 = 255; n10 >= 0; --n10) {
                        final int a4 = l.a(n9, n10, n8);
                        if (set.get(a4) || set.get(a4 + 1)) {
                            array[n8 << 4 | n9] = axw.a(n9, n10, n8);
                            ++n9;
                            continue Label_0574;
                        }
                    }
                    array[n8 << 4 | n9] = aox.a.t();
                    continue;
                }
            }
        }
        return new l(axw.b, axw.c, set, array, (Map<String, List<et>>)hashMap, System.currentTimeMillis());
    }
    
    public p() {
        this((byte)0);
    }
    
    private p(final byte b) {
        this.a = 0;
        this.a = new g[1024];
    }
    
    public final void a(final g g) {
        if (this.a >= this.a.length - 1) {
            this.a = Arrays.copyOf(this.a, this.a.length << 1);
        }
        ++this.a;
        g.d = this.a;
        this.b(this.a[this.a] = g);
    }
    
    public final void b(final g g) {
        int d2;
        int d = (d2 = g.d) >>> 1;
        final double c = g.c;
        for (g g2 = this.a[d]; d2 > 1 && g2.c > c; d = (d2 = d) >>> 1, g2 = this.a[d]) {
            this.a[d2] = g2;
            this.a[d] = g;
            g.d = d;
            g2.d = d2;
        }
    }
    
    public final g a() {
        if (this.a == 0) {
            throw new IllegalStateException();
        }
        final g g = this.a[1];
        final g g2 = this.a[this.a];
        this.a[1] = g2;
        g2.d = 1;
        this.a[this.a] = null;
        --this.a;
        g.d = -1;
        if (this.a < 2) {
            return g;
        }
        int d = 1;
        int d2 = 2;
        final double c = g2.c;
        do {
            g g3;
            double c2 = (g3 = this.a[d2]).c;
            if (d2 < this.a) {
                final g g4;
                final double c3 = (g4 = this.a[d2 + 1]).c;
                if (c2 > c3) {
                    ++d2;
                    c2 = c3;
                    g3 = g4;
                }
            }
            if (c <= c2) {
                break;
            }
            this.a[d] = g3;
            this.a[d2] = g2;
            g2.d = d2;
            g3.d = d;
            d = d2;
        } while ((d2 <<= 1) <= this.a);
        return g;
    }
}
