// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.event.listener;

import baritone.api.event.events.PathEvent;
import baritone.api.event.events.BlockInteractEvent;
import baritone.api.event.events.SprintStateEvent;
import baritone.api.event.events.RotationMoveEvent;
import baritone.api.event.events.PacketEvent;
import baritone.api.event.events.WorldEvent;
import baritone.api.event.events.RenderEvent;
import baritone.api.event.events.ChunkEvent;
import baritone.api.event.events.TabCompleteEvent;
import baritone.api.event.events.ChatEvent;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.event.events.TickEvent;

public interface AbstractGameEventListener extends IGameEventListener
{
    default void onTick(final TickEvent tickEvent) {
    }
    
    default void onPlayerUpdate(final PlayerUpdateEvent playerUpdateEvent) {
    }
    
    default void onSendChatMessage(final ChatEvent chatEvent) {
    }
    
    default void onPreTabComplete(final TabCompleteEvent tabCompleteEvent) {
    }
    
    default void onChunkEvent(final ChunkEvent chunkEvent) {
    }
    
    default void onRenderPass(final RenderEvent renderEvent) {
    }
    
    default void onWorldEvent(final WorldEvent worldEvent) {
    }
    
    default void onSendPacket(final PacketEvent packetEvent) {
    }
    
    default void onReceivePacket(final PacketEvent packetEvent) {
    }
    
    default void onPlayerRotationMove(final RotationMoveEvent rotationMoveEvent) {
    }
    
    default void onPlayerSprintState(final SprintStateEvent sprintStateEvent) {
    }
    
    default void onBlockInteract(final BlockInteractEvent blockInteractEvent) {
    }
    
    default void onPlayerDeath() {
    }
    
    default void onPathEvent(final PathEvent pathEvent) {
    }
}
