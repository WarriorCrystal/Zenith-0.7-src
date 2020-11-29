// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.BitSet;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.io.FileInputStream;
import java.util.Iterator;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import java.io.FileOutputStream;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.ArrayList;
import baritone.api.utils.BlockUtils;
import baritone.api.cache.ICachedRegion;

public final class m implements ICachedRegion
{
    private final l[][] a;
    private final int a;
    private final int b;
    private final int c;
    private boolean a;
    
    m(final int a, final int b, final int c) {
        this.a = new l[32][32];
        this.a = a;
        this.b = b;
        this.a = false;
        this.c = c;
    }
    
    @Override
    public final awt getBlock(final int n, int n2, int n3) {
        final l l;
        if ((l = this.a[n >> 4][n3 >> 4]) == null) {
            return null;
        }
        final l i = l;
        final int n4 = n & 0xF;
        final int n5 = n2;
        final int n6 = n3 & 0xF;
        final int c = this.c;
        final int n7 = n6;
        n3 = n5;
        n2 = n4;
        final l j = i;
        final int a = baritone.l.a(n2, n3, n7);
        final l k = j;
        final int bitIndex = a;
        final gl a2 = gl.a(k.a.get(bitIndex), k.a.get(bitIndex + 1));
        n2 |= n7 << 4;
        if (j.a[n2] == n3 && a2 != gl.c) {
            return j.a[n2];
        }
        final String s;
        if (j.a != null && (s = (String)j.a.get(a)) != null) {
            return BlockUtils.stringToBlockRequired(s).t();
        }
        if (a2 == gl.d) {
            if (n3 == 127 && c == -1) {
                return aox.h.t();
            }
            if (n3 < 5 && c == 0) {
                return aox.Z.t();
            }
        }
        final gl gl = a2;
        final int n8 = c;
        switch (q.a[gl.ordinal()]) {
            case 1: {
                return aox.a.t();
            }
            case 2: {
                return aox.j.t();
            }
            case 3: {
                return aox.l.t();
            }
            case 4: {
                switch (n8) {
                    case -1: {
                        return aox.aV.t();
                    }
                    default: {
                        return aox.b.t();
                    }
                    case 1: {
                        return aox.bH.t();
                    }
                }
                break;
            }
            default: {
                return null;
            }
        }
    }
    
    @Override
    public final boolean isCached(final int n, final int n2) {
        return this.a[n >> 4][n2 >> 4] != null;
    }
    
    public final ArrayList<et> a(final String s) {
        final ArrayList<et> list = new ArrayList<et>();
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                final ArrayList<et> a;
                if (this.a[i][j] != null && (a = this.a[i][j].a(s)) != null) {
                    list.addAll(a);
                }
            }
        }
        return list;
    }
    
    public final synchronized void a(final int n, final int n2, final l l) {
        this.a[n][n2] = l;
        this.a = true;
    }
    
    public final synchronized void a(String s) {
        if (!this.a) {
            return;
        }
        this.a();
        try {
            final Path value;
            if (!Files.exists(value = Paths.get(s, new String[0]), new LinkOption[0])) {
                Files.createDirectories(value, (FileAttribute<?>[])new FileAttribute[0]);
            }
            System.out.println("Saving region " + this.a + "," + this.b + " to disk " + value);
            final Path a;
            if (!Files.exists(a = a(value, this.a, this.b), new LinkOption[0])) {
                Files.createFile(a, (FileAttribute<?>[])new FileAttribute[0]);
            }
            s = (String)new FileOutputStream(a.toFile());
            Throwable t = null;
            try {
                final GZIPOutputStream out = new GZIPOutputStream((OutputStream)s, 16384);
                Throwable t2 = null;
                try {
                    final DataOutputStream dataOutputStream = new DataOutputStream(out);
                    Throwable t3 = null;
                    try {
                        dataOutputStream.writeInt(456022910);
                        for (int i = 0; i < 32; ++i) {
                            for (int j = 0; j < 32; ++j) {
                                final l l;
                                if ((l = this.a[i][j]) == null) {
                                    dataOutputStream.write(0);
                                }
                                else {
                                    dataOutputStream.write(1);
                                    final byte[] byteArray = l.a.toByteArray();
                                    dataOutputStream.write(byteArray);
                                    dataOutputStream.write(new byte[16384 - byteArray.length]);
                                }
                            }
                        }
                        for (int k = 0; k < 32; ++k) {
                            for (int n = 0; n < 32; ++n) {
                                if (this.a[k][n] != null) {
                                    for (int n2 = 0; n2 < 256; ++n2) {
                                        dataOutputStream.writeUTF(BlockUtils.blockToString(this.a[k][n].a[n2].u()));
                                    }
                                }
                            }
                        }
                        for (int n3 = 0; n3 < 32; ++n3) {
                            for (int n4 = 0; n4 < 32; ++n4) {
                                if (this.a[n3][n4] != null) {
                                    final Map<String, List<et>> a2 = this.a[n3][n4].a;
                                    dataOutputStream.writeShort(a2.entrySet().size());
                                    for (final Map.Entry<String, List<et>> entry : a2.entrySet()) {
                                        dataOutputStream.writeUTF(entry.getKey());
                                        dataOutputStream.writeShort(entry.getValue().size());
                                        for (final et et : entry.getValue()) {
                                            dataOutputStream.writeByte((byte)(et.r() << 4 | et.p()));
                                            dataOutputStream.writeByte((byte)et.q());
                                        }
                                    }
                                }
                            }
                        }
                        for (int n5 = 0; n5 < 32; ++n5) {
                            for (int n6 = 0; n6 < 32; ++n6) {
                                if (this.a[n5][n6] != null) {
                                    dataOutputStream.writeLong(this.a[n5][n6].a);
                                }
                            }
                        }
                        dataOutputStream.close();
                    }
                    catch (Throwable t5) {
                        final Throwable t4 = t5;
                        t3 = t5;
                        throw t4;
                    }
                    finally {
                        if (t3 != null) {
                            try {
                                dataOutputStream.close();
                            }
                            catch (Throwable exception) {
                                t3.addSuppressed(exception);
                            }
                        }
                        else {
                            dataOutputStream.close();
                        }
                    }
                    out.close();
                }
                catch (Throwable t7) {
                    final Throwable t6 = t7;
                    t2 = t7;
                    throw t6;
                }
                finally {
                    if (t2 != null) {
                        try {
                            out.close();
                        }
                        catch (Throwable exception2) {
                            t2.addSuppressed(exception2);
                        }
                    }
                    else {
                        out.close();
                    }
                }
                ((FileOutputStream)s).close();
            }
            catch (Throwable t9) {
                final Throwable t8 = t9;
                t = t9;
                throw t8;
            }
            finally {
                if (t != null) {
                    try {
                        ((FileOutputStream)s).close();
                    }
                    catch (Throwable exception3) {
                        t.addSuppressed(exception3);
                    }
                }
                else {
                    ((FileOutputStream)s).close();
                }
            }
            this.a = false;
            System.out.println("Saved region successfully");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public final synchronized void b(String s) {
        try {
            final Path value;
            if (!Files.exists(value = Paths.get(s, new String[0]), new LinkOption[0])) {
                Files.createDirectories(value, (FileAttribute<?>[])new FileAttribute[0]);
            }
            final Path a;
            if (!Files.exists(a = a(value, this.a, this.b), new LinkOption[0])) {
                return;
            }
            System.out.println("Loading region " + this.a + "," + this.b + " from disk " + value);
            final long n = System.nanoTime() / 1000000L;
            final FileInputStream in = new FileInputStream(a.toFile());
            Throwable t = null;
            try {
                s = (String)new GZIPInputStream(in, 32768);
                Throwable t2 = null;
                try {
                    final DataInputStream dataInputStream = new DataInputStream((InputStream)s);
                    Throwable t3 = null;
                    try {
                        final int int1;
                        if ((int1 = dataInputStream.readInt()) != 456022910) {
                            throw new IOException("Bad magic value ".concat(String.valueOf(int1)));
                        }
                        final boolean[][] array = new boolean[32][32];
                        final BitSet[][] array2 = new BitSet[32][32];
                        final Map[][] array3 = new Map[32][32];
                        final awt[][][] array4 = new awt[32][32][];
                        final long[][] array5 = new long[32][32];
                        for (int i = 0; i < 32; ++i) {
                            for (int j = 0; j < 32; ++j) {
                                switch (dataInputStream.read()) {
                                    case 1: {
                                        final byte[] array6 = new byte[16384];
                                        dataInputStream.readFully(array6);
                                        array2[i][j] = BitSet.valueOf(array6);
                                        array3[i][j] = new HashMap();
                                        array4[i][j] = new awt[256];
                                        array[i][j] = true;
                                        break;
                                    }
                                    case 0: {
                                        break;
                                    }
                                    default: {
                                        throw new IOException("Malformed stream");
                                    }
                                }
                            }
                        }
                        for (int k = 0; k < 32; ++k) {
                            for (int l = 0; l < 32; ++l) {
                                if (array[k][l]) {
                                    for (int n2 = 0; n2 < 256; ++n2) {
                                        array4[k][l][n2] = BlockUtils.stringToBlockRequired(dataInputStream.readUTF()).t();
                                    }
                                }
                            }
                        }
                        for (int n3 = 0; n3 < 32; ++n3) {
                            for (int n4 = 0; n4 < 32; ++n4) {
                                if (array[n3][n4]) {
                                    for (int n5 = dataInputStream.readShort() & 0xFFFF, n6 = 0; n6 < n5; ++n6) {
                                        final String utf;
                                        BlockUtils.stringToBlockRequired(utf = dataInputStream.readUTF());
                                        final ArrayList<et> list = new ArrayList<et>();
                                        array3[n3][n4].put(utf, list);
                                        int n7;
                                        if ((n7 = (dataInputStream.readShort() & 0xFFFF)) == 0) {
                                            n7 = 65536;
                                        }
                                        for (int n8 = 0; n8 < n7; ++n8) {
                                            final byte byte1 = dataInputStream.readByte();
                                            list.add(new et(byte1 & 0xF, dataInputStream.readByte() & 0xFF, byte1 >>> 4 & 0xF));
                                        }
                                    }
                                }
                            }
                        }
                        for (int n9 = 0; n9 < 32; ++n9) {
                            for (int n10 = 0; n10 < 32; ++n10) {
                                if (array[n9][n10]) {
                                    array5[n9][n10] = dataInputStream.readLong();
                                }
                            }
                        }
                        for (int n11 = 0; n11 < 32; ++n11) {
                            for (int n12 = 0; n12 < 32; ++n12) {
                                if (array[n11][n12]) {
                                    this.a[n11][n12] = new l(n11 + (this.a << 5), n12 + (this.b << 5), array2[n11][n12], array4[n11][n12], array3[n11][n12], array5[n11][n12]);
                                }
                            }
                        }
                        dataInputStream.close();
                    }
                    catch (Throwable t5) {
                        final Throwable t4 = t5;
                        t3 = t5;
                        throw t4;
                    }
                    finally {
                        if (t3 != null) {
                            try {
                                dataInputStream.close();
                            }
                            catch (Throwable exception) {
                                t3.addSuppressed(exception);
                            }
                        }
                        else {
                            dataInputStream.close();
                        }
                    }
                    ((GZIPInputStream)s).close();
                }
                catch (Throwable t7) {
                    final Throwable t6 = t7;
                    t2 = t7;
                    throw t6;
                }
                finally {
                    if (t2 != null) {
                        try {
                            ((GZIPInputStream)s).close();
                        }
                        catch (Throwable exception2) {
                            t2.addSuppressed(exception2);
                        }
                    }
                    else {
                        ((GZIPInputStream)s).close();
                    }
                }
                in.close();
            }
            catch (Throwable t9) {
                final Throwable t8 = t9;
                t = t9;
                throw t8;
            }
            finally {
                if (t != null) {
                    try {
                        in.close();
                    }
                    catch (Throwable exception3) {
                        t.addSuppressed(exception3);
                    }
                }
                else {
                    in.close();
                }
            }
            this.a();
            this.a = false;
            final long n13 = System.nanoTime() / 1000000L;
            final PrintStream out = System.out;
            final StringBuilder sb = new StringBuilder("Loaded region successfully in ");
            final long n14 = n13;
            final long n15 = n;
            out.println(sb.append(n14 - n15).append("ms").toString());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public final synchronized void a() {
        final long longValue;
        if ((longValue = baritone.a.a().cachedChunksExpirySeconds.value) < 0L) {
            return;
        }
        final long currentTimeMillis;
        final long n = (currentTimeMillis = System.currentTimeMillis()) - longValue * 1000L;
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                if (this.a[i][j] != null && this.a[i][j].a < n) {
                    System.out.println("Removing chunk " + (i + 32 * this.a) + "," + (j + 32 * this.b) + " because it was cached " + (currentTimeMillis - this.a[i][j].a) / 1000L + " seconds ago, and max age is " + longValue);
                    this.a[i][j] = null;
                }
            }
        }
    }
    
    public final synchronized l a() {
        l l = null;
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                if (this.a[i][j] != null && (l == null || this.a[i][j].a > l.a)) {
                    l = this.a[i][j];
                }
            }
        }
        return l;
    }
    
    @Override
    public final int getX() {
        return this.a;
    }
    
    @Override
    public final int getZ() {
        return this.b;
    }
    
    private static Path a(final Path path, final int i, final int j) {
        return Paths.get(path.toString(), "r." + i + "." + j + ".bcr");
    }
}
