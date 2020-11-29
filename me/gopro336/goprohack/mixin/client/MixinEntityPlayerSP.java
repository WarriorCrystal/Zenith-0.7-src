//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin.client;

import me.gopro336.goprohack.events.player.EventPlayerJump;
import me.gopro336.goprohack.events.player.EventPlayerSendChatMessage;
import me.gopro336.goprohack.events.player.EventPlayerPushOutOfBlocks;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import me.gopro336.goprohack.events.player.EventPlayerSwingArm;
import net.minecraft.util.EnumHand;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.gopro336.goprohack.events.MinecraftEvent;
import org.spongepowered.asm.mixin.injection.Inject;
import me.gopro336.goprohack.GoproHackMod;
import me.gopro336.goprohack.events.player.EventPlayerMove;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.MoverType;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityPlayerSP.class })
public abstract class MixinEntityPlayerSP extends MixinAbstractClientPlayer
{
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V"))
    public void closeScreen(final EntityPlayerSP entityPlayerSP) {
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"))
    public void closeScreen(final Minecraft minecraft, final GuiScreen screen) {
    }
    
    @Inject(method = { "move" }, at = { @At("HEAD") }, cancellable = true)
    public void move(final MoverType type, final double x, final double y, final double z, final CallbackInfo p_Info) {
        final EventPlayerMove event = new EventPlayerMove(type, x, y, z);
        GoproHackMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            super.move(type, event.X, event.Y, event.Z);
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("HEAD") }, cancellable = true)
    public void OnPreUpdateWalkingPlayer(final CallbackInfo p_Info) {
        final EventPlayerMotionUpdate l_Event = new EventPlayerMotionUpdate(MinecraftEvent.Era.PRE);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("RETURN") }, cancellable = true)
    public void OnPostUpdateWalkingPlayer(final CallbackInfo p_Info) {
        final EventPlayerMotionUpdate l_Event = new EventPlayerMotionUpdate(MinecraftEvent.Era.POST);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "onUpdate" }, at = { @At("HEAD") }, cancellable = true)
    public void onUpdate(final CallbackInfo p_Info) {
        final EventPlayerUpdate l_Event = new EventPlayerUpdate();
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "swingArm" }, at = { @At("HEAD") }, cancellable = true)
    public void swingArm(final EnumHand p_Hand, final CallbackInfo p_Info) {
        final EventPlayerSwingArm l_Event = new EventPlayerSwingArm(p_Hand);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "pushOutOfBlocks(DDD)Z" }, at = { @At("HEAD") }, cancellable = true)
    public void pushOutOfBlocks(final double x, final double y, final double z, final CallbackInfoReturnable<Boolean> callbackInfo) {
        final EventPlayerPushOutOfBlocks l_Event = new EventPlayerPushOutOfBlocks(x, y, z);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            callbackInfo.setReturnValue(false);
        }
    }
    
    @Inject(method = { "sendChatMessage" }, at = { @At("HEAD") }, cancellable = true)
    public void swingArm(final String p_Message, final CallbackInfo p_Info) {
        final EventPlayerSendChatMessage l_Event = new EventPlayerSendChatMessage(p_Message);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Override
    public void jump() {
        try {
            final EventPlayerJump l_Event = new EventPlayerJump(this.motionX, this.motionZ);
            GoproHackMod.EVENT_BUS.post(l_Event);
            if (!l_Event.isCancelled()) {
                super.jump();
            }
        }
        catch (Exception v3) {
            v3.printStackTrace();
        }
    }
}
