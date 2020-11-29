// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.io.File;
import org.spongepowered.asm.mixin.Mixin;
import baritone.ga;

@Mixin({ aye.class })
public class MixinAnvilChunkLoader implements ga
{
    @Shadow
    @Final
    private File d;
    
    @Override
    public File getChunkSaveLocation() {
        return this.d;
    }
}
