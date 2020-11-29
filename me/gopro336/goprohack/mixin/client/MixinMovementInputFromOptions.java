// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.gopro336.goprohack.events.player.EventPlayerUpdateMoveState;
import me.gopro336.goprohack.GoproHackMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.util.MovementInput;

@Mixin(value = { MovementInputFromOptions.class }, priority = 10000)
public abstract class MixinMovementInputFromOptions extends MovementInput
{
    @Inject(method = { "updatePlayerMoveState" }, at = { @At("RETURN") })
    public void updatePlayerMoveStateReturn(final CallbackInfo callback) {
        GoproHackMod.EVENT_BUS.post(new EventPlayerUpdateMoveState());
    }
}
