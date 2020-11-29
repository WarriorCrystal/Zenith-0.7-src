// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import baritone.gb;

@Mixin({ qw.class })
public abstract class MixinBitArray implements gb
{
    @Shadow
    @Final
    private long[] a;
    @Shadow
    @Final
    private int b;
    @Shadow
    @Final
    private long c;
    @Shadow
    @Final
    private int d;
    
    @Unique
    @Override
    public int[] toArray() {
        final int[] array = new int[this.d];
        for (int i = 0, n = this.b - 1; i < this.d; ++i, n += this.b) {
            final int n3;
            final int n2 = (n3 = i * this.b) >> 6;
            final int n4 = n3 & 0x3F;
            final int n5 = n >> 6;
            final long n6 = this.a[n2] >>> n4;
            if (n2 == n5) {
                array[i] = (int)(n6 & this.c);
            }
            else {
                array[i] = (int)((n6 | this.a[n5] << 64 - n4) & this.c);
            }
        }
        return array;
    }
}
