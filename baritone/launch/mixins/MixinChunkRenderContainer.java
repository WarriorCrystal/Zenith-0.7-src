// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.lwjgl.opengl.GL14;
import baritone.a;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bun.class })
public class MixinChunkRenderContainer
{
    @Redirect(method = { "preRenderChunk" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/chunk/RenderChunk.getPosition()Lnet/minecraft/util/math/BlockPos;"))
    private et getPosition(final bxr bxr) {
        if (a.a().renderCachedChunks.value && !bib.z().E() && bib.z().f.f(bxr.k()).f()) {
            bus.e();
            bus.m();
            GL14.glBlendColor(0.0f, 0.0f, 0.0f, (float)a.a().cachedChunksOpacity.value);
            bus.a(32771, 32772, 1, 0);
        }
        return bxr.k();
    }
}
