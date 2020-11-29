// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import baritone.a;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bvf.class })
public class MixinVboRenderList
{
    @Redirect(method = { "renderChunkLayer" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.popMatrix()V"))
    private void popMatrix() {
        if (a.a().renderCachedChunks.value && !bib.z().E()) {
            bus.a(770, 771, 1, 0);
        }
        bus.H();
    }
}
