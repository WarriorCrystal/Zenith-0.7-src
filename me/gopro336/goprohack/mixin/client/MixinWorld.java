// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin.client;

import me.gopro336.goprohack.events.entity.EventEntityRemoved;
import me.gopro336.goprohack.events.entity.EventEntityAdded;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.Entity;
import me.gopro336.goprohack.events.world.EventWorldSetBlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.gopro336.goprohack.GoproHackMod;
import me.gopro336.goprohack.events.render.EventRenderRainStrength;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ World.class })
public class MixinWorld
{
    @Inject(method = { "getRainStrength" }, at = { @At("HEAD") }, cancellable = true)
    public void getRainStrength(final float delta, final CallbackInfoReturnable<Float> p_Callback) {
        final EventRenderRainStrength l_Event = new EventRenderRainStrength();
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Callback.cancel();
            p_Callback.setReturnValue(0.0f);
        }
    }
    
    @Inject(method = { "setBlockState" }, at = { @At("HEAD") }, cancellable = true)
    public void setBlockState(final BlockPos pos, final IBlockState newState, final int flags, final CallbackInfoReturnable<Boolean> p_CallBack) {
        final EventWorldSetBlockState l_Event = new EventWorldSetBlockState(pos, newState, flags);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_CallBack.cancel();
            p_CallBack.setReturnValue(false);
        }
    }
    
    @Inject(method = { "onEntityAdded" }, at = { @At("HEAD") }, cancellable = true)
    public void onEntityAdded(final Entity p_Entity, final CallbackInfo p_Info) {
        final EventEntityAdded l_Event = new EventEntityAdded(p_Entity);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "onEntityRemoved" }, at = { @At("HEAD") }, cancellable = true)
    public void onEntityRemoved(final Entity p_Entity, final CallbackInfo p_Info) {
        final EventEntityRemoved l_Event = new EventEntityRemoved(p_Entity);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
}
