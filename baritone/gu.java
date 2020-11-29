// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

public final class gu extends gq
{
    public gu(final fy fy) {
        final String l;
        if (!(l = fy.l("Materials")).equals("Alpha")) {
            throw new IllegalStateException("bad schematic ".concat(String.valueOf(l)));
        }
        this.x = fy.h("Width");
        this.y = fy.h("Height");
        this.z = fy.h("Length");
        final byte[] m = fy.m("Blocks");
        final byte[] i = fy.m("Data");
        byte[] array = null;
        if (fy.e("AddBlocks")) {
            final byte[] j;
            array = new byte[(j = fy.m("AddBlocks")).length << 1];
            for (int k = 0; k < j.length; ++k) {
                array[k << 1] = (byte)(j[k] >> 4 & 0xF);
                array[(k << 1) + 1] = (byte)(j[k] & 0xF);
            }
        }
        this.a = new awt[this.x][this.z][this.y];
        for (int n = 0; n < this.y; ++n) {
            for (int n2 = 0; n2 < this.z; ++n2) {
                for (int n3 = 0; n3 < this.x; ++n3) {
                    final int n4 = (n * this.z + n2) * this.x + n3;
                    int n5 = m[n4] & 0xFF;
                    if (array != null) {
                        n5 |= array[n4] << 8;
                    }
                    this.a[n3][n2][n] = ((aow)aow.h.a(n5)).a(i[n4] & 0xFF);
                }
            }
        }
    }
}
