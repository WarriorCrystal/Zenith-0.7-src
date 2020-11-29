// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ blq.class })
public abstract class MixinTabCompleter
{
    @Shadow
    @Final
    protected bje a;
    @Shadow
    protected boolean d;
    @Unique
    protected boolean dontComplete;
    
    public MixinTabCompleter() {
        this.dontComplete = false;
    }
    
    @Shadow
    public abstract void a(final String... p0);
    
    @Inject(method = { "requestCompletions" }, at = { @At("HEAD") }, cancellable = true)
    private void onRequestCompletions(final String s, final CallbackInfo callbackInfo) {
    }
}
