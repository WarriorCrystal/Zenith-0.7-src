// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import org.spongepowered.asm.mixin.gen.Invoker;
import java.net.URI;
import org.spongepowered.asm.mixin.Mixin;
import baritone.gf;

@Mixin({ blk.class })
public abstract class MixinGuiScreen implements gf
{
    @Invoker("openWebLink")
    @Override
    public abstract void openLink(final URI p0);
}
