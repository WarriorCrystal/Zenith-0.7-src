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
import baritone.api.utils.accessor.IItemStack;

@Mixin({ aip.class })
public abstract class MixinItemStack implements IItemStack
{
    @Shadow
    @Final
    private ain e;
    @Shadow
    private int h;
    @Unique
    private int baritoneHash;
    
    private void recalculateHash() {
        this.baritoneHash = ((this.e == null) ? -1 : (this.e.hashCode() + this.h));
    }
    
    @Inject(method = { "<init>*" }, at = { @At("RETURN") })
    private void onInit(final CallbackInfo callbackInfo) {
        this.recalculateHash();
    }
    
    @Inject(method = { "setItemDamage" }, at = { @At("TAIL") })
    private void onItemDamageSet(final CallbackInfo callbackInfo) {
        this.recalculateHash();
    }
    
    @Override
    public int getBaritoneHash() {
        return this.baritoneHash;
    }
}
