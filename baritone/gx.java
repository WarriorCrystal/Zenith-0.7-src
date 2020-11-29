// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;

public final class gx
{
    public final int a;
    private final byte[] a;
    public final int b;
    
    private gx(final int a) {
        this.a = a;
        this.a = a(this.a);
        this.b = this.a.length;
    }
    
    private static byte[] a(int n) {
        final ByteArrayList list = new ByteArrayList();
        while ((n & 0x80) != 0x0) {
            ((ByteList)list).add((byte)((n & 0x7F) | 0x80));
            n >>>= 7;
        }
        ((ByteList)list).add((byte)n);
        return ((ByteList)list).toByteArray();
    }
    
    public static gx a(final byte[] array, int n) {
        int n2 = 0;
        int n3 = 0;
        byte b;
        do {
            b = array[n++];
            n2 |= (b & 0x7F) << n3++ * 7;
            if (n3 > 5) {
                throw new IllegalArgumentException("VarInt size cannot exceed 5 bytes");
            }
        } while ((b & 0x80) != 0x0);
        return new gx(n2);
    }
}
