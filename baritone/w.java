// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.utils.BetterBlockPos;
import baritone.api.cache.ICachedWorld;
import java.util.Collections;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.List;
import baritone.api.utils.BlockOptionalMetaLookup;
import baritone.api.utils.IPlayerContext;
import baritone.api.cache.IWorldScanner;

public enum w implements IWorldScanner
{
    a;
    
    private static final int[] a;
    
    @Override
    public final List<et> scanChunkRadius(final IPlayerContext playerContext, final BlockOptionalMetaLookup blockOptionalMetaLookup, final int n3, final int n4, int n5) {
        final ArrayList<et> list = new ArrayList<et>();
        if (blockOptionalMetaLookup.blocks().isEmpty()) {
            return list;
        }
        final brx brx = (brx)playerContext.world().B();
        final int n6 = n5;
        n5 = n6 * n6;
        final int n7 = playerContext.playerFeet().p() >> 4;
        final int n8 = playerContext.playerFeet().r() >> 4;
        final int q = playerContext.playerFeet().q();
        final Object o;
        final int[] array = IntStream.range(0, 16).boxed().sorted(Comparator.comparingInt(n2 -> Math.abs((int)((int)n2 - (o >> 4))))).mapToInt(n -> n).toArray();
        int n9 = 0;
        boolean b = false;
        while (true) {
            boolean b2 = true;
            boolean b3 = false;
            for (int i = -n9; i <= n9; ++i) {
                for (int j = -n9; j <= n9; ++j) {
                    final int n10 = i;
                    final int n11 = n10 * n10;
                    final int n12 = j;
                    if (n11 + n12 * n12 == n9) {
                        b3 = true;
                        final int n13 = i + n7;
                        final int n14 = j + n8;
                        final axw a;
                        if ((a = brx.a(n13, n14)) != null) {
                            b2 = false;
                            if (a(n13 << 4, n14 << 4, a, blockOptionalMetaLookup, list, n3, n4, q, array)) {
                                b = true;
                            }
                        }
                    }
                }
            }
            if ((b2 && b3) || (list.size() >= n3 && (n9 > n5 || (n9 > 1 && b)))) {
                break;
            }
            ++n9;
        }
        return list;
    }
    
    @Override
    public final List<et> scanChunk(final IPlayerContext playerContext, final BlockOptionalMetaLookup blockOptionalMetaLookup, final amn amn, final int n, final int n2) {
        if (blockOptionalMetaLookup.blocks().isEmpty()) {
            return Collections.emptyList();
        }
        final axw a = ((brx)playerContext.world().B()).a(amn.a, amn.b);
        final int q = playerContext.playerFeet().q();
        if (a == null || a.f()) {
            return Collections.emptyList();
        }
        final ArrayList<et> list = new ArrayList<et>();
        a(amn.a << 4, amn.b << 4, a, blockOptionalMetaLookup, list, n, n2, q, w.a);
        return list;
    }
    
    @Override
    public final int repack(final IPlayerContext playerContext) {
        return this.repack(playerContext, 40);
    }
    
    @Override
    public final int repack(final IPlayerContext playerContext, int n) {
        final axr b = playerContext.world().B();
        final ICachedWorld cachedWorld = playerContext.worldData().getCachedWorld();
        final BetterBlockPos playerFeet;
        final int n2 = (playerFeet = playerContext.playerFeet()).p() >> 4;
        final int n3 = playerFeet.r() >> 4;
        int i = n2 - n;
        final int n4 = n3 - n;
        final int n5 = n2 + n;
        final int n6 = n3 + n;
        n = 0;
        while (i <= n5) {
            for (int j = n4; j <= n6; ++j) {
                final axw a;
                if ((a = b.a(i, j)) != null && !a.f()) {
                    ++n;
                    cachedWorld.queueForPacking(a);
                }
            }
            ++i;
        }
        return n;
    }
    
    private static boolean a(final int n, final int n2, final axw axw, final BlockOptionalMetaLookup blockOptionalMetaLookup, final Collection<et> collection, final int n3, final int n4, final int n5, final int[] array) {
        final axx[] h = axw.h();
        boolean b = false;
        for (int i = 0; i < 16; ++i) {
            final int n6 = array[i];
            final axx axx;
            if ((axx = h[n6]) != null) {
                final int n7 = n6 << 4;
                final gc gc;
                final int[] storageArray = (gc = (gc)axx.g()).storageArray();
                for (int j = 0; j < 4096; ++j) {
                    if (blockOptionalMetaLookup.has(gc.getAtPalette(storageArray[j]))) {
                        final int n8 = n7 | (j >> 8 & 0xF);
                        if (collection.size() >= n3) {
                            if (Math.abs(n8 - n5) < n4) {
                                b = true;
                            }
                            else if (b) {
                                return true;
                            }
                        }
                        collection.add(new et(n | (j & 0xF), n8, n2 | (j >> 4 & 0xF)));
                    }
                }
            }
        }
        return b;
    }
    
    static {
        a = IntStream.range(0, 16).toArray();
    }
}
