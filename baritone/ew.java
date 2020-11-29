// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.function.Predicate;

enum ew
{
    a((aps)aox.aj), 
    b((aps)aox.cb), 
    c((aps)aox.cc), 
    d((aps)aox.cZ), 
    e(aox.aU, p0 -> true), 
    f(aox.bk, p0 -> true), 
    g(aox.bB, awt -> (int)awt.c((axj)ase.a) >= 3), 
    h("SUGARCANE", (aow)aox.aM) {
        ex(final String s, final aow aow) {
        }
        
        @Override
        public final boolean a(final amu amu, final et et, final awt awt) {
            return !baritone.a.a().replantCrops.value || amu.o(et.b()).u() instanceof ati;
        }
    }, 
    i("CACTUS", (aow)aox.aK) {
        ey(final String s, final aow aow) {
        }
        
        @Override
        public final boolean a(final amu amu, final et et, final awt awt) {
            return !baritone.a.a().replantCrops.value || amu.o(et.b()).u() instanceof ape;
        }
    };
    
    public final aow a;
    private Predicate<awt> a;
    
    private ew(final aps aps) {
        this((aow)aps, aps::z);
    }
    
    private ew(final aow a, final Predicate<awt> a2) {
        this.a = a;
        this.a = a2;
    }
    
    public boolean a(final amu amu, final et et, final awt awt) {
        return this.a.test(awt);
    }
}
