// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.function.Predicate;

enum bc
{
    a(vq.class::isInstance), 
    b(aed.class::isInstance);
    
    final Predicate<vg> a;
    
    private bc(final Predicate<vg> a) {
        this.a = a;
    }
}
