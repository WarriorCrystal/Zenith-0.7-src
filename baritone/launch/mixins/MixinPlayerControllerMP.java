// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.Mixin;
import baritone.gg;

@Mixin({ bsa.class })
public abstract class MixinPlayerControllerMP implements gg
{
    @Accessor
    @Override
    public abstract void setIsHittingBlock(final boolean p0);
    
    @Accessor
    @Override
    public abstract et getCurrentBlock();
    
    @Invoker
    @Override
    public abstract void callSyncCurrentPlayItem();
}
