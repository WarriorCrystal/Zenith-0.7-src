// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Iterator;
import baritone.api.utils.Helper;
import baritone.api.utils.BetterBlockPos;
import baritone.api.pathing.calc.IPath;
import baritone.api.utils.IPlayerContext;
import it.unimi.dsi.fastutil.longs.Long2DoubleOpenHashMap;

public final class gi
{
    public final Long2DoubleOpenHashMap a;
    
    public gi(final IPlayerContext playerContext, final IPath path, final cj cj) {
        this(path, cj);
        for (final gh gh : gh.a(playerContext)) {
            final Long2DoubleOpenHashMap a = this.a;
            final gh gh2 = gh;
            for (int i = -gh.d; i <= gh2.d; ++i) {
                for (int j = -gh2.d; j <= gh2.d; ++j) {
                    for (int k = -gh2.d; k <= gh2.d; ++k) {
                        final int n = i;
                        final int n2 = n * n;
                        final int n3 = j;
                        final int n4 = n2 + n3 * n3;
                        final int n5 = k;
                        if (n4 + n5 * n5 <= gh2.d * gh2.d) {
                            final long longHash = BetterBlockPos.longHash(gh2.a + i, gh2.b + j, gh2.c + k);
                            a.put(longHash, a.get(longHash) * gh2.a);
                        }
                    }
                }
            }
        }
        Helper.HELPER.logDebug("Favoring size: " + this.a.size());
    }
    
    private gi(final IPath path, final cj cj) {
        (this.a = new Long2DoubleOpenHashMap()).defaultReturnValue(1.0);
        final double d;
        if ((d = cj.d) != 1.0 && path != null) {
            path.positions().forEach(betterBlockPos -> this.a.put(BetterBlockPos.longHash(betterBlockPos), d));
        }
    }
}
