//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.gopro336.goprohack.GoproHackMod;
import me.gopro336.goprohack.events.player.EventPlayerIsPotionActive;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.potion.Potion;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityLivingBase.class })
public abstract class MixinEntityLivingBase extends MixinEntity
{
    @Shadow
    public void jump() {
    }
    
    @Inject(method = { "isPotionActive" }, at = { @At("HEAD") }, cancellable = true)
    public void isPotionActive(final Potion potionIn, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final EventPlayerIsPotionActive l_Event = new EventPlayerIsPotionActive(potionIn);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}
