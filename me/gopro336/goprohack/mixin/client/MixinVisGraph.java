// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.gopro336.goprohack.GoproHackMod;
import me.gopro336.goprohack.events.blocks.EventSetOpaqueCube;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.chunk.VisGraph;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ VisGraph.class })
public class MixinVisGraph
{
    @Inject(method = { "setOpaqueCube" }, at = { @At("HEAD") }, cancellable = true)
    public void setOpaqueCube(final BlockPos pos, final CallbackInfo info) {
        final EventSetOpaqueCube l_Event = new EventSetOpaqueCube();
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            info.cancel();
        }
    }
}
