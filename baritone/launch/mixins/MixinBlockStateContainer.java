// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import baritone.gb;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import baritone.gc;

@Mixin({ axp.class })
public abstract class MixinBlockStateContainer implements gc
{
    @Shadow
    protected qw b;
    @Shadow
    protected aya c;
    
    @Override
    public awt getAtPalette(final int n) {
        return this.c.a(n);
    }
    
    @Override
    public int[] storageArray() {
        return ((gb)this.b).toArray();
    }
}
