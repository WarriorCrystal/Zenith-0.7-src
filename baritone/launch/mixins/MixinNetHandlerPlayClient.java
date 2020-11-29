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

@Mixin({ brz.class })
public class MixinNetHandlerPlayClient
{
    @Inject(method = { "handleChunkData" }, at = { @At(value = "INVOKE", target = "net/minecraft/world/chunk/Chunk.read(Lnet/minecraft/network/PacketBuffer;IZ)V") })
    private void preRead(final je je, final CallbackInfo callbackInfo) {
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            final bud player;
            if ((player = (baritone = iterator.next()).getPlayerContext().player()) != null && player.d == (brz)this) {
                baritone.getGameEventHandler().onChunkEvent(new ChunkEvent(EventState.PRE, je.e() ? ChunkEvent$Type.POPULATE_FULL : ChunkEvent$Type.POPULATE_PARTIAL, je.b(), je.c()));
            }
        }
    }
    
    @Inject(method = { "handleChunkData" }, at = { @At("RETURN") })
    private void postHandleChunkData(final je je, final CallbackInfo callbackInfo) {
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            final bud player;
            if ((player = (baritone = iterator.next()).getPlayerContext().player()) != null && player.d == (brz)this) {
                baritone.getGameEventHandler().onChunkEvent(new ChunkEvent(EventState.POST, je.e() ? ChunkEvent$Type.POPULATE_FULL : ChunkEvent$Type.POPULATE_PARTIAL, je.b(), je.c()));
            }
        }
    }
    
    @Inject(method = { "handleCombatEvent" }, at = { @At(value = "INVOKE", target = "net/minecraft/client/Minecraft.displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V") })
    private void onPlayerDeath(final jo jo, final CallbackInfo callbackInfo) {
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            final bud player;
            if ((player = (baritone = iterator.next()).getPlayerContext().player()) != null && player.d == (brz)this) {
                baritone.getGameEventHandler().onPlayerDeath();
            }
        }
    }
}
