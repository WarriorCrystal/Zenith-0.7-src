// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.ArrayList;
import java.util.List;
import baritone.api.cache.IRememberedInventory;
import java.util.Optional;
import java.util.Iterator;
import java.nio.file.OpenOption;
import java.util.Collection;
import io.netty.buffer.Unpooled;
import java.nio.file.NoSuchFileException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Path;
import baritone.api.cache.IContainerMemory;

public final class r implements IContainerMemory
{
    private final Path a;
    private final Map<et, s> a;
    
    public r(final Path path) {
        this.a = new HashMap<et, s>();
        this.a = path;
        try {
            this.a(Files.readAllBytes(path));
        }
        catch (NoSuchFileException ex2) {
            this.a.clear();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.a.clear();
        }
    }
    
    private void a(final byte[] array) {
        gy gy;
        for (int int1 = (gy = new gy(Unpooled.wrappedBuffer(array))).readInt(), i = 0; i < int1; ++i) {
            final int int2 = gy.readInt();
            final int int3 = gy.readInt();
            final int int4 = gy.readInt();
            final s s;
            (s = new s((byte)0)).a.addAll(a(gy));
            final s s2 = s;
            s2.a = s2.a.size();
            s.b = -1;
            if (!s.a.isEmpty()) {
                this.a.put(new et(int2, int3, int4), s);
            }
        }
    }
    
    public final synchronized void a() {
        if (!baritone.a.a().containerMemory.value) {
            return;
        }
        gy a;
        (a = new gy(Unpooled.buffer(0, Integer.MAX_VALUE))).writeInt(this.a.size());
        for (final Map.Entry<et, s> entry : this.a.entrySet()) {
            a = a(entry.getValue().getContents(), new gy(new gy(new gy(a.writeInt(entry.getKey().p())).writeInt(entry.getKey().q())).writeInt(entry.getKey().r())));
        }
        Files.write(this.a, a.array(), new OpenOption[0]);
    }
    
    public final synchronized void a(final et key, final int n, final int n2) {
        final s s;
        (s = this.a.computeIfAbsent(key, p0 -> new s((byte)0))).b = n;
        s.a = n2;
    }
    
    public final synchronized Optional<s> a(final int n) {
        return this.a.values().stream().filter(s -> s.b == n).findFirst();
    }
    
    private synchronized s a(final et et) {
        return this.a.get(et);
    }
    
    @Override
    public final synchronized Map<et, IRememberedInventory> getRememberedInventories() {
        return new HashMap<et, IRememberedInventory>(this.a);
    }
    
    public static List<aip> a(final byte[] array) {
        return a(new gy(Unpooled.wrappedBuffer(array)));
    }
    
    private static List<aip> a(final gy gy) {
        final int int1 = gy.readInt();
        final ArrayList<aip> list = new ArrayList<aip>();
        for (int i = 0; i < int1; ++i) {
            list.add(gy.k());
        }
        return list;
    }
    
    public static byte[] a(final List<aip> list) {
        return a(list, new gy(Unpooled.buffer(0, Integer.MAX_VALUE))).array();
    }
    
    private static gy a(final List<aip> list, gy a) {
        a = new gy(a.writeInt(list.size()));
        final Iterator<aip> iterator = list.iterator();
        while (iterator.hasNext()) {
            a = a.a((aip)iterator.next());
        }
        return a;
    }
}
