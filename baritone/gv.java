// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Iterator;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;

public final class gv extends gq
{
    public gv(final fy fy) {
        this.x = fy.h("Width");
        this.y = fy.h("Height");
        this.z = fy.h("Length");
        this.a = new awt[this.x][this.z][this.y];
        final Int2ObjectArrayMap int2ObjectArrayMap = new Int2ObjectArrayMap();
        fy p;
        for (final String s : (p = fy.p("Palette")).c()) {
            final int h = p.h(s);
            final gw a;
            if ((a = b(s)) == null) {
                throw new IllegalArgumentException("Unable to parse palette tag");
            }
            final awt a2;
            if ((a2 = gw.a(a)) == null) {
                throw new IllegalArgumentException("Unable to deserialize palette tag");
            }
            int2ObjectArrayMap.put(h, (Object)a2);
        }
        final byte[] m = fy.m("BlockData");
        final int[] array = new int[this.x * this.y * this.z];
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (n >= m.length) {
                throw new IllegalArgumentException("No remaining bytes in BlockData for complete schematic");
            }
            final gx a3 = gx.a(m, n);
            array[i] = a3.a;
            n += a3.b;
        }
        for (int j = 0; j < this.y; ++j) {
            for (int k = 0; k < this.z; ++k) {
                for (int l = 0; l < this.x; ++l) {
                    final int i2 = (j * this.z + k) * this.x + l;
                    final awt awt;
                    if ((awt = (awt)int2ObjectArrayMap.get(array[i2])) == null) {
                        throw new IllegalArgumentException("Invalid Palette Index ".concat(String.valueOf(i2)));
                    }
                    this.a[l][k][j] = awt;
                }
            }
        }
    }
}
