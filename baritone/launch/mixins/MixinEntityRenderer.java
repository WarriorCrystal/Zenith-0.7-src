// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.Iterator;
import baritone.api.event.events.RenderEvent;
import baritone.api.IBaritone;
import baritone.api.BaritoneAPI;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ buq.class })
public class MixinEntityRenderer
{
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE_STRING", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", args = { "ldc=hand" }) })
    private void renderWorldPass(final int n, final float n2, final long n3, final CallbackInfo callbackInfo) {
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            iterator.next().getGameEventHandler().onRenderPass(new RenderEvent(n2));
        }
    }
}
