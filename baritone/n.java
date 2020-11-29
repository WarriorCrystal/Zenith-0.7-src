// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.cache.ICachedRegion;
import java.util.List;
import baritone.api.cache.IWorldData;
import java.util.Iterator;
import baritone.api.IBaritone;
import baritone.api.BaritoneAPI;
import java.util.Collection;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.nio.file.Path;
import java.util.concurrent.LinkedBlockingQueue;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import baritone.api.utils.Helper;
import baritone.api.cache.ICachedWorld;

public final class n implements ICachedWorld, Helper
{
    private Long2ObjectMap<m> a;
    private final String a;
    private final LinkedBlockingQueue<axw> a;
    private final int a;
    
    n(final Path obj, final int a) {
        this.a = (Long2ObjectMap<m>)new Long2ObjectOpenHashMap();
        this.a = new LinkedBlockingQueue<axw>();
        if (!Files.exists(obj, new LinkOption[0])) {
            try {
                Files.createDirectories(obj, (FileAttribute<?>[])new FileAttribute[0]);
            }
            catch (IOException ex2) {}
        }
        this.a = obj.toString();
        this.a = a;
        System.out.println("Cached world directory: ".concat(String.valueOf(obj)));
        baritone.a.a().execute(new o(this, (byte)0));
        baritone.a.a().execute(() -> {
            try {
                Thread.sleep(30000L);
                while (true) {
                    this.save();
                    Thread.sleep(600000L);
                }
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    @Override
    public final void queueForPacking(final axw e) {
        try {
            this.a.put(e);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public final boolean isCached(final int n, final int n2) {
        final m a;
        return (a = this.a(n >> 9, n2 >> 9)) != null && a.isCached(n & 0x1FF, n2 & 0x1FF);
    }
    
    @Override
    public final ArrayList<et> getLocationsOf(final String s, final int n, int n2, int n3, final int n4) {
        final ArrayList<et> list = new ArrayList<et>();
        n2 >>= 9;
        n3 >>= 9;
        for (int i = 0; i <= n4; ++i) {
            for (int j = -i; j <= i; ++j) {
                for (int k = -i; k <= i; ++k) {
                    final int n5 = j;
                    final int n6 = n5 * n5;
                    final int n7 = k;
                    final m b;
                    if (n6 + n7 * n7 == i && (b = this.b(j + n2, k + n3)) != null) {
                        list.addAll(b.a(s));
                    }
                }
            }
            if (list.size() >= n) {
                return list;
            }
        }
        return list;
    }
    
    @Override
    public final void save() {
        if (!baritone.a.a().chunkCaching.value) {
            System.out.println("Not saving to disk; chunk caching is disabled.");
            this.a().forEach(m -> {
                if (m != null) {
                    m.a();
                }
                return;
            });
            this.a();
            return;
        }
        final long n = System.nanoTime() / 1000000L;
        this.a().parallelStream().forEach(i -> {
            if (i != null) {
                i.a(this.a);
            }
            return;
        });
        System.out.println("World save took " + (System.nanoTime() / 1000000L - n) + "ms");
        this.a();
    }
    
    private synchronized void a() {
        if (!baritone.a.a().pruneRegionsFromRAM.value) {
            return;
        }
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (true) {
            while (iterator.hasNext()) {
                final IBaritone baritone;
                final IWorldData currentWorld;
                if ((currentWorld = (baritone = iterator.next()).getWorldProvider().getCurrentWorld()) != null && currentWorld.getCachedWorld() == this) {
                    final et playerFeet = baritone.getPlayerContext().playerFeet();
                    final et et = playerFeet;
                    final Iterator<m> iterator2 = this.a().iterator();
                    while (iterator2.hasNext()) {
                        final m m;
                        if ((m = iterator2.next()) != null) {
                            final int n = (m.getX() << 9) + 256 - et.p();
                            final int n2 = (m.getZ() << 9) + 256 - et.r();
                            final int n3 = n;
                            final int n4 = n3 * n3;
                            final int n5 = n2;
                            if (Math.sqrt(n4 + n5 * n5) <= 1024.0) {
                                continue;
                            }
                            if (!baritone.a.a().censorCoordinates.value) {
                                this.logDebug("Deleting cached region " + m.getX() + "," + m.getZ() + " from ram");
                            }
                            this.a.remove(a(m.getX(), m.getZ()));
                        }
                    }
                    return;
                }
            }
            l l = null;
            final Iterator<m> iterator3 = this.a().iterator();
            while (iterator3.hasNext()) {
                final m i;
                final l a;
                if ((i = iterator3.next()) != null && (a = i.a()) != null) {
                    if (l != null && l.a >= a.a) {
                        continue;
                    }
                    l = a;
                }
            }
            final et playerFeet = (l == null) ? new et(0, 0, 0) : new et((l.a << 4) + 8, 0, (l.b << 4) + 8);
            continue;
        }
    }
    
    private synchronized List<m> a() {
        return new ArrayList<m>((Collection<? extends m>)this.a.values());
    }
    
    @Override
    public final void reloadAllFromDisk() {
        final long n = System.nanoTime() / 1000000L;
        this.a().forEach(m -> {
            if (m != null) {
                m.b(this.a);
            }
            return;
        });
        System.out.println("World load took " + (System.nanoTime() / 1000000L - n) + "ms");
    }
    
    public final synchronized m a(final int n, final int n2) {
        return (m)this.a.get(a(n, n2));
    }
    
    public final synchronized m b(final int n, final int n2) {
        final m m;
        final m i;
        return (m)this.a.computeIfAbsent((Object)a(n, n2), p2 -> {
            m = new m(n, n2, this.a);
            i.b(this.a);
            return m;
        });
    }
    
    private static long a(final int n, final int n2) {
        if (!a(n, n2)) {
            return 0L;
        }
        return ((long)n & 0xFFFFFFFFL) | ((long)n2 & 0xFFFFFFFFL) << 32;
    }
    
    private static boolean a(final int n, final int n2) {
        return n <= 58594 && n >= -58594 && n2 <= 58594 && n2 >= -58594;
    }
}
