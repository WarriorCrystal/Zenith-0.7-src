// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import baritone.ge;

@Mixin({ on.class })
public class MixinChunkProviderServer implements ge
{
    @Shadow
    @Final
    private ayf d;
    
    @Override
    public ayf getChunkLoader() {
        return this.d;
    }
}
