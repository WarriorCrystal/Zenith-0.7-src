// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import com.google.common.collect.ImmutableMap;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = { "net.minecraft.block.state.BlockStateContainer$StateImplementation" })
public abstract class MixinStateImplementation
{
    @Shadow
    @Final
    private ImmutableMap<axj<?>, Comparable<?>> b;
    @Unique
    private int hashCode;
    
    @Inject(method = { "<init>*" }, at = { @At("RETURN") })
    private void onInit(final CallbackInfo callbackInfo) {
        this.hashCode = this.b.hashCode();
    }
    
    @Overwrite
    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
