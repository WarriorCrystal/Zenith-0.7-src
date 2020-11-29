// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin.client;

import me.gopro336.goprohack.events.player.EventPlayerDestroyBlock;
import me.gopro336.goprohack.events.player.EventPlayerClickBlock;
import me.gopro336.goprohack.events.player.EventPlayerResetBlockRemoving;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.gopro336.goprohack.events.player.EventPlayerDamageBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.gopro336.goprohack.GoproHackMod;
import me.gopro336.goprohack.events.blocks.EventGetBlockReachDistance;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ PlayerControllerMP.class })
public class MixinPlayerControllerMP
{
    @Inject(method = { "getBlockReachDistance" }, at = { @At("HEAD") }, cancellable = true)
    public void getBlockReachDistance(final CallbackInfoReturnable<Float> callback) {
        final EventGetBlockReachDistance l_Event = new EventGetBlockReachDistance();
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.BlockReachDistance > 0.0f) {
            callback.setReturnValue(l_Event.BlockReachDistance);
            callback.cancel();
        }
    }
    
    @Inject(method = { "onPlayerDamageBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void onPlayerDamageBlock(final BlockPos posBlock, final EnumFacing directionFacing, final CallbackInfoReturnable<Boolean> p_Info) {
        final EventPlayerDamageBlock l_Event = new EventPlayerDamageBlock(posBlock, directionFacing);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.setReturnValue(false);
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "resetBlockRemoving" }, at = { @At("HEAD") }, cancellable = true)
    public void resetBlockRemoving(final CallbackInfo p_Info) {
        final EventPlayerResetBlockRemoving l_Event = new EventPlayerResetBlockRemoving();
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "clickBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void clickBlock(final BlockPos loc, final EnumFacing face, final CallbackInfoReturnable<Boolean> callback) {
        final EventPlayerClickBlock l_Event = new EventPlayerClickBlock(loc, face);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            callback.setReturnValue(false);
            callback.cancel();
        }
    }
    
    @Inject(method = { "onPlayerDestroyBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void onPlayerDestroyBlock(final BlockPos pos, final CallbackInfoReturnable<Boolean> info) {
        final EventPlayerDestroyBlock l_Event = new EventPlayerDestroyBlock(pos);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            info.setReturnValue(false);
            info.cancel();
        }
    }
}
