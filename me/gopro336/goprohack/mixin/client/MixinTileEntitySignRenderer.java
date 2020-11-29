// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.gopro336.goprohack.GoproHackMod;
import me.gopro336.goprohack.events.render.EventRenderSign;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ TileEntitySignRenderer.class })
public class MixinTileEntitySignRenderer
{
    @Inject(method = { "render" }, at = { @At("HEAD") }, cancellable = true)
    public void render(final TileEntitySign te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha, final CallbackInfo p_Info) {
        final EventRenderSign l_Event = new EventRenderSign();
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
}
