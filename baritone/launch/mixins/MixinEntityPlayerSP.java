// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import baritone.d;
import baritone.api.event.events.SprintStateEvent;
import org.spongepowered.asm.mixin.injection.Redirect;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.event.events.type.EventState;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import baritone.api.IBaritone;
import baritone.api.BaritoneAPI;
import baritone.api.event.events.ChatEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bud.class })
public class MixinEntityPlayerSP
{
    @Inject(method = { "sendChatMessage" }, at = { @At("HEAD") }, cancellable = true)
    private void sendChatMessage(final String s, final CallbackInfo callbackInfo) {
        final ChatEvent chatEvent = new ChatEvent(s);
        final IBaritone baritoneForPlayer;
        if ((baritoneForPlayer = BaritoneAPI.getProvider().getBaritoneForPlayer((bud)this)) == null) {
            return;
        }
        baritoneForPlayer.getGameEventHandler().onSendChatMessage(chatEvent);
        if (chatEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "onUpdate" }, at = { @At(value = "INVOKE", target = "net/minecraft/client/entity/EntityPlayerSP.isRiding()Z", shift = At.Shift.BY, by = -3) })
    private void onPreUpdate(final CallbackInfo callbackInfo) {
        final IBaritone baritoneForPlayer;
        if ((baritoneForPlayer = BaritoneAPI.getProvider().getBaritoneForPlayer((bud)this)) != null) {
            baritoneForPlayer.getGameEventHandler().onPlayerUpdate(new PlayerUpdateEvent(EventState.PRE));
        }
    }
    
    @Inject(method = { "onUpdate" }, at = { @At(value = "INVOKE", target = "net/minecraft/client/entity/EntityPlayerSP.onUpdateWalkingPlayer()V", shift = At.Shift.BY, by = 2) })
    private void onPostUpdate(final CallbackInfo callbackInfo) {
        final IBaritone baritoneForPlayer;
        if ((baritoneForPlayer = BaritoneAPI.getProvider().getBaritoneForPlayer((bud)this)) != null) {
            baritoneForPlayer.getGameEventHandler().onPlayerUpdate(new PlayerUpdateEvent(EventState.POST));
        }
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "FIELD", target = "net/minecraft/entity/player/PlayerCapabilities.allowFlying:Z"))
    private boolean isAllowFlying(final aeb aeb) {
        final IBaritone baritoneForPlayer;
        if ((baritoneForPlayer = BaritoneAPI.getProvider().getBaritoneForPlayer((bud)this)) == null) {
            return aeb.c;
        }
        return !baritoneForPlayer.getPathingBehavior().isPathing() && aeb.c;
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "net/minecraft/client/settings/KeyBinding.isKeyDown()Z"))
    private boolean isKeyDown(final bhy bhy) {
        final IBaritone baritoneForPlayer;
        if ((baritoneForPlayer = BaritoneAPI.getProvider().getBaritoneForPlayer((bud)this)) == null) {
            return bhy.e();
        }
        final SprintStateEvent sprintStateEvent = new SprintStateEvent();
        baritoneForPlayer.getGameEventHandler().onPlayerSprintState(sprintStateEvent);
        if (sprintStateEvent.getState() != null) {
            return sprintStateEvent.getState();
        }
        return baritoneForPlayer == BaritoneAPI.getProvider().getPrimaryBaritone() && bhy.e();
    }
    
    @Inject(method = { "updateRidden" }, at = { @At("HEAD") })
    private void updateRidden(final CallbackInfo callbackInfo) {
        final IBaritone baritoneForPlayer;
        final d d;
        if ((baritoneForPlayer = BaritoneAPI.getProvider().getBaritoneForPlayer((bud)this)) != null && (d = (d)baritoneForPlayer.getLookBehavior()).a != null) {
            d.a.player().v = d.a.getYaw();
        }
    }
}
