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

public interface IGameEventListener
{
    void onTick(final TickEvent p0);
    
    void onPlayerUpdate(final PlayerUpdateEvent p0);
    
    void onSendChatMessage(final ChatEvent p0);
    
    void onPreTabComplete(final TabCompleteEvent p0);
    
    void onChunkEvent(final ChunkEvent p0);
    
    void onRenderPass(final RenderEvent p0);
    
    void onWorldEvent(final WorldEvent p0);
    
    void onSendPacket(final PacketEvent p0);
    
    void onReceivePacket(final PacketEvent p0);
    
    void onPlayerRotationMove(final RotationMoveEvent p0);
    
    void onPlayerSprintState(final SprintStateEvent p0);
    
    void onBlockInteract(final BlockInteractEvent p0);
    
    void onPlayerDeath();
    
    void onPathEvent(final PathEvent p0);
}
