// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.BaritoneAPI;
import baritone.api.utils.Helper;
import java.awt.Color;
import baritone.api.Settings;

public interface fq
{
    public static final bve a;
    public static final buk a;
    public static final bzf a;
    public static final Settings a = BaritoneAPI.getSettings();
    
    default void a(final Color color, final float n) {
        final float[] colorComponents;
        bus.c((colorComponents = color.getColorComponents(null))[0], colorComponents[1], colorComponents[2], n);
    }
    
    default void a(final Color color, final float n, final float n2, final boolean b) {
        bus.m();
        bus.g();
        bus.a(770, 771, 1, 0);
        a(color, n);
        bus.d(n2);
        bus.z();
        bus.a(false);
        if (b) {
            bus.j();
        }
    }
    
    default void a(final Color color, final float n, final boolean b) {
        a(color, 0.4f, n, b);
    }
    
    default void a(final boolean b) {
        if (b) {
            bus.k();
        }
        bus.a(true);
        bus.y();
        bus.f();
        bus.l();
    }
    
    default void a(bhb d) {
        d = d.d(-fq.a.h, -fq.a.i, -fq.a.j);
        fq.a.a(1, cdy.e);
        fq.a.b(d.a, d.b, d.c).d();
        fq.a.b(d.d, d.b, d.c).d();
        fq.a.b(d.d, d.b, d.c).d();
        fq.a.b(d.d, d.b, d.f).d();
        fq.a.b(d.d, d.b, d.f).d();
        fq.a.b(d.a, d.b, d.f).d();
        fq.a.b(d.a, d.b, d.f).d();
        fq.a.b(d.a, d.b, d.c).d();
        fq.a.b(d.a, d.e, d.c).d();
        fq.a.b(d.d, d.e, d.c).d();
        fq.a.b(d.d, d.e, d.c).d();
        fq.a.b(d.d, d.e, d.f).d();
        fq.a.b(d.d, d.e, d.f).d();
        fq.a.b(d.a, d.e, d.f).d();
        fq.a.b(d.a, d.e, d.f).d();
        fq.a.b(d.a, d.e, d.c).d();
        fq.a.b(d.a, d.b, d.c).d();
        fq.a.b(d.a, d.e, d.c).d();
        fq.a.b(d.d, d.b, d.c).d();
        fq.a.b(d.d, d.e, d.c).d();
        fq.a.b(d.d, d.b, d.f).d();
        fq.a.b(d.d, d.e, d.f).d();
        fq.a.b(d.a, d.b, d.f).d();
        fq.a.b(d.a, d.e, d.f).d();
        fq.a.b();
    }
    
    default void a(final bhb bhb, final double n) {
        a(bhb.c(n, n, n));
    }
    
    default static {
        a = (a = bve.a()).c();
        a = Helper.mc.ac();
    }
}
