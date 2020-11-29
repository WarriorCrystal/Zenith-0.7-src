// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import org.spongepowered.asm.mixin.Mixin;
import baritone.gd;

@Mixin({ brx.class })
public class MixinChunkProviderClient implements gd
{
    @Shadow
    @Final
    private Long2ObjectMap<axw> c;
    
    @Override
    public Long2ObjectMap<axw> loadedChunks() {
        return this.c;
    }
}
