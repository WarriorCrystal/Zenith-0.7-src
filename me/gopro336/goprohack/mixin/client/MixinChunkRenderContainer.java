// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.gopro336.goprohack.events.render.EventRenderChunkContainer;
import me.gopro336.goprohack.GoproHackMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.ChunkRenderContainer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ChunkRenderContainer.class })
public class MixinChunkRenderContainer
{
    @Inject(method = { "preRenderChunk" }, at = { @At(value = "RETURN", target = "Lnet/minecraft/client/renderer/chunk/RenderChunk;getPosition()Lnet/minecraft/util/math/BlockPos/MutableBlockPos;") })
    private void preRenderChunk(final RenderChunk renderChunk, final CallbackInfo callbackInfo) {
        GoproHackMod.EVENT_BUS.post(new EventRenderChunkContainer(renderChunk));
    }
}
