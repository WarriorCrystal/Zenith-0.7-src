// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import baritone.api.utils.IPlayerContext;

public final class gh
{
    final int a;
    final int b;
    final int c;
    final double a;
    final int d;
    private final int e;
    
    private gh(final et et, final double n, final int n2) {
        this(et.p(), et.q(), et.r(), n, n2);
    }
    
    private gh(final int a, final int b, final int c, final double a2, final int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.a = a2;
        this.d = d;
        this.e = d * d;
    }
    
    public static List<gh> a(final IPlayerContext playerContext) {
        if (!a.a().avoidance.value) {
            return Collections.emptyList();
        }
        final ArrayList<gh> list = new ArrayList<gh>();
        final double doubleValue = a.a().mobSpawnerAvoidanceCoefficient.value;
        final double doubleValue2 = a.a().mobAvoidanceCoefficient.value;
        if (doubleValue != 1.0) {
            playerContext.worldData().getCachedWorld().getLocationsOf("mob_spawner", 1, playerContext.playerFeet().a, playerContext.playerFeet().c, 2).forEach(et -> list.add(new gh(et, doubleValue, a.a().mobSpawnerAvoidanceRadius.value)));
        }
        if (doubleValue2 != 1.0) {
            final gh gh;
            final double n;
            final List<gh> list2;
            playerContext.world().e.stream().filter(vg -> vg instanceof ade).filter(vg2 -> !(vg2 instanceof adn) || playerContext.player().aw() < 0.5).filter(adf -> !(adf instanceof adf) || adf.dp()).filter(acu -> !(acu instanceof acu) || acu.do()).forEach(vg3 -> {
                new gh(new et(vg3), n, a.a().mobAvoidanceRadius.value);
                list2.add(gh);
                return;
            });
        }
        return list;
    }
}
