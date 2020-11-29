// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.gopro336.goprohack.GoproHackMod;
import me.gopro336.goprohack.events.render.EventRenderEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RenderManager.class })
public class MixinRenderManager
{
    @Inject(method = { "shouldRender" }, at = { @At("HEAD") }, cancellable = true)
    public void isPotionActive(final Entity entityIn, final ICamera camera, final double camX, final double camY, final double camZ, final CallbackInfoReturnable<Boolean> callback) {
        final EventRenderEntity event = new EventRenderEntity(entityIn, camera, camX, camY, camZ);
        GoproHackMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callback.setReturnValue(false);
        }
    }
}
