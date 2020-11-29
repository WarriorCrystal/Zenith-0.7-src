//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin.client;

import me.gopro336.goprohack.events.render.EventRenderOrientCamera;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.events.render.EventRenderUpdateLightmap;
import me.gopro336.goprohack.events.render.EventRenderHurtCameraEffect;
import me.gopro336.goprohack.events.render.EventRenderHand;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.ArrayList;
import me.gopro336.goprohack.events.render.EventRenderGetEntitiesINAABBexcluding;
import java.util.List;
import com.google.common.base.Predicate;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.gopro336.goprohack.GoproHackMod;
import me.gopro336.goprohack.events.render.EventRenderSetupFog;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityRenderer.class })
public class MixinEntityRenderer
{
    @Inject(method = { "setupFog" }, at = { @At("HEAD") }, cancellable = true)
    public void setupFog(final int startCoords, final float partialTicks, final CallbackInfo p_Info) {
        final EventRenderSetupFog l_Event = new EventRenderSetupFog(startCoords, partialTicks);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(final WorldClient worldClient, final Entity entityIn, final AxisAlignedBB boundingBox, final Predicate predicate) {
        final EventRenderGetEntitiesINAABBexcluding l_Event = new EventRenderGetEntitiesINAABBexcluding(worldClient, entityIn, boundingBox, predicate);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            return new ArrayList<Entity>();
        }
        return (List<Entity>)worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
    }
    
    @Inject(method = { "renderHand" }, at = { @At("HEAD") }, cancellable = true)
    private void renderHand(final float partialTicks, final int pass, final CallbackInfo p_Info) {
        final EventRenderHand l_Event = new EventRenderHand(partialTicks, pass);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void hurtCameraEffect(final float ticks, final CallbackInfo info) {
        final EventRenderHurtCameraEffect l_Event = new EventRenderHurtCameraEffect(ticks);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "updateLightmap" }, at = { @At("HEAD") }, cancellable = true)
    private void updateLightmap(final float partialTicks, final CallbackInfo p_Info) {
        final EventRenderUpdateLightmap l_Event = new EventRenderUpdateLightmap(partialTicks);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = At.Shift.AFTER) })
    private void renderWorldPassPost(final int pass, final float partialTicks, final long finishTimeNano, final CallbackInfo callbackInfo) {
        RenderUtil.updateModelViewProjectionMatrix();
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"), expect = 0)
    private RayTraceResult rayTraceBlocks(final WorldClient worldClient, final Vec3d start, final Vec3d end) {
        final EventRenderOrientCamera event = new EventRenderOrientCamera();
        GoproHackMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            return null;
        }
        return worldClient.rayTraceBlocks(start, end);
    }
}
