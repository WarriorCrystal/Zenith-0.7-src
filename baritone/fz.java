// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.List;
import java.util.HashMap;
import java.util.function.Function;
import java.util.Map;

public final class fz
{
    private final Map<aow, Double> a;
    private final Function<aow, Double> a;
    private final bud a;
    
    public fz(final bud a) {
        this.a = new HashMap<aow, Double>();
        this.a = a;
        if (baritone.a.a().considerPotionEffects.value) {
            double n2 = 1.0;
            if (this.a.a(vb.c)) {
                n2 = 1.0 * (1.0 + (this.a.b(vb.c).c() + 1) * 0.2);
            }
            if (this.a.a(vb.d)) {
                switch (this.a.b(vb.d).c()) {
                    case 0: {
                        n2 *= 0.3;
                        break;
                    }
                    case 1: {
                        n2 *= 0.09;
                        break;
                    }
                    case 2: {
                        n2 *= 0.0027;
                        break;
                    }
                    default: {
                        n2 *= 8.1E-4;
                        break;
                    }
                }
            }
            this.a = ((Function<Object, Double>)(n -> n2 * n)).compose((Function<? super aow, ?>)this::a);
            return;
        }
        this.a = this::a;
    }
    
    public final double a(final awt awt) {
        return this.a.computeIfAbsent(awt.u(), this.a);
    }
    
    private static int a(final aip aip) {
        if (aip.c() instanceof ahq) {
            return ain$a.valueOf(((ahq)aip.c()).h()).ordinal();
        }
        return -1;
    }
    
    private static boolean a(final aip aip) {
        return alm.a(alo.t, aip) > 0;
    }
    
    public final int a(final aow aow, final boolean b) {
        int n = 0;
        double n2 = Double.NEGATIVE_INFINITY;
        int a = Integer.MIN_VALUE;
        int n3 = 0;
        final awt t = aow.t();
        for (int i = 0; i < 9; ++i) {
            final aip a3;
            final double a2 = a(a3 = this.a.bv.a(i), t);
            final boolean a4 = a(a3);
            if (a2 > n2) {
                n2 = a2;
                n = i;
                a = a(a3);
                n3 = (a4 ? 1 : 0);
            }
            else {
                final int a5;
                if (a2 == n2 && (((a5 = a(a3)) < a && (a4 || n3 == 0)) || (b && n3 == 0 && a4))) {
                    n2 = a2;
                    n = i;
                    a = a5;
                    n3 = (a4 ? 1 : 0);
                }
            }
        }
        return n;
    }
    
    private double a(final aow aow) {
        return a(this.a.bv.a(this.a(aow, false)), aow.t()) * (baritone.a.a().blocksToAvoidBreaking.value.contains(aow) ? 0.1 : 1.0);
    }
    
    public static double a(final aip aip, final awt awt) {
        final float b;
        if ((b = awt.b((amu)null, (et)null)) < 0.0f) {
            return -1.0;
        }
        float a;
        final int a2;
        if ((a = aip.a(awt)) > 1.0f && (a2 = alm.a(alo.s, aip)) > 0 && !aip.b()) {
            final float n = a;
            final int n2 = a2;
            a = n + (n2 * n2 + 1);
        }
        final float n3 = a / b;
        if (awt.a().l() || (!aip.b() && aip.b(awt))) {
            return n3 / 30.0f;
        }
        return n3 / 100.0f;
    }
}
