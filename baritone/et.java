// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.io.Reader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import com.google.gson.GsonBuilder;
import java.nio.file.Path;
import baritone.api.utils.MyChunkPos;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

final class et implements es
{
    private final boolean a;
    private final LongOpenHashSet a;
    private final MyChunkPos[] a;
    private /* synthetic */ en a;
    
    private et(final en a, final Path path, final boolean a2) {
        this.a = a;
        this.a = a2;
        this.a = (MyChunkPos[])new GsonBuilder().create().fromJson((Reader)new InputStreamReader(Files.newInputStream(path, new OpenOption[0])), (Class)MyChunkPos[].class);
        a.logDirect("Loaded " + this.a.length + " positions");
        this.a = new LongOpenHashSet();
        MyChunkPos[] a3;
        for (int length = (a3 = this.a).length, i = 0; i < length; ++i) {
            final MyChunkPos myChunkPos = a3[i];
            this.a.add(amn.a(myChunkPos.x, myChunkPos.z));
        }
    }
    
    @Override
    public final int a(final int n, final int n2) {
        if (this.a.contains(amn.a(n, n2)) ^ this.a) {
            return eu.a;
        }
        return eu.c;
    }
    
    @Override
    public final int a() {
        if (!this.a) {
            return Integer.MAX_VALUE;
        }
        int n = 0;
        final eq eq = new eq(this.a, (byte)0);
        MyChunkPos[] a;
        for (int length = (a = this.a).length, i = 0; i < length; ++i) {
            final MyChunkPos myChunkPos = a[i];
            if (eq.a(myChunkPos.x, myChunkPos.z) != eu.a && ++n >= baritone.a.a().exploreChunkSetMinimumSize.value) {
                return n;
            }
        }
        return n;
    }
}
