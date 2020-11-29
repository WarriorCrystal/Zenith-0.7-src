// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.gopro336.goprohack.events.render.EventRenderChunk;
import net.minecraft.util.math.BlockPos;
import me.gopro336.goprohack.GoproHackMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.chunk.RenderChunk;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RenderChunk.class })
public class MixinRenderChunk
{
    @Inject(method = { "setPosition" }, at = { @At("INVOKE") })
    private void setPosition(final int x, final int y, final int z, final CallbackInfo callbackInfo) {
        GoproHackMod.EVENT_BUS.post(new EventRenderChunk((RenderChunk)this, new BlockPos(x, y, z)));
    }
}
