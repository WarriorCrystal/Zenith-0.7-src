// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bkn$a.class })
public abstract class MixinChatTabCompleter extends MixinTabCompleter
{
    @Inject(method = { "complete" }, at = { @At("HEAD") }, cancellable = true)
    private void onComplete(final CallbackInfo callbackInfo) {
        if (this.dontComplete) {
            callbackInfo.cancel();
        }
    }
}
