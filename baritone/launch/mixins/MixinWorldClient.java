// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.Iterator;
import baritone.api.event.events.ChunkEvent;
import baritone.api.event.events.ChunkEvent$Type;
import baritone.api.event.events.type.EventState;
import baritone.api.IBaritone;
import baritone.api.BaritoneAPI;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bsb.class })
public class MixinWorldClient
{
    @Inject(method = { "doPreChunk" }, at = { @At("HEAD") })
    private void preDoPreChunk(final int n, final int n2, final boolean b, final CallbackInfo callbackInfo) {
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().world() == (bsb)this) {
                baritone.getGameEventHandler().onChunkEvent(new ChunkEvent(EventState.PRE, b ? ChunkEvent$Type.LOAD : ChunkEvent$Type.UNLOAD, n, n2));
            }
        }
    }
    
    @Inject(method = { "doPreChunk" }, at = { @At("RETURN") })
    private void postDoPreChunk(final int n, final int n2, final boolean b, final CallbackInfo callbackInfo) {
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().world() == (bsb)this) {
                baritone.getGameEventHandler().onChunkEvent(new ChunkEvent(EventState.POST, b ? ChunkEvent$Type.LOAD : ChunkEvent$Type.UNLOAD, n, n2));
            }
        }
    }
}
