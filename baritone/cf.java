// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.event.events.PathEvent;
import baritone.api.event.events.BlockInteractEvent;
import baritone.api.event.events.SprintStateEvent;
import baritone.api.event.events.RotationMoveEvent;
import baritone.api.event.events.PacketEvent;
import baritone.api.event.events.WorldEvent;
import baritone.api.event.events.RenderEvent;
import java.util.function.Consumer;
import baritone.api.event.events.ChunkEvent$Type;
import baritone.api.event.events.type.EventState;
import baritone.api.event.events.ChunkEvent;
import baritone.api.event.events.TabCompleteEvent;
import baritone.api.event.events.ChatEvent;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.event.events.TickEvent$Type;
import baritone.api.event.events.TickEvent;
import java.util.concurrent.CopyOnWriteArrayList;
import baritone.api.event.listener.IGameEventListener;
import java.util.List;
import baritone.api.utils.Helper;
import baritone.api.event.listener.IEventBus;

public final class cf implements IEventBus, Helper
{
    private final a a;
    private final List<IGameEventListener> a;
    
    public cf(final a a) {
        this.a = new CopyOnWriteArrayList<IGameEventListener>();
        this.a = a;
    }
    
    @Override
    public final void onTick(final TickEvent tickEvent) {
        Label_0044: {
            if (tickEvent.getType() == TickEvent$Type.IN) {
                try {
                    this.a.a = new fn(this.a.getPlayerContext(), true);
                    break Label_0044;
                }
                catch (Exception ex) {}
            }
            this.a.a = null;
        }
        this.a.forEach(gameEventListener -> gameEventListener.onTick(tickEvent));
    }
    
    @Override
    public final void onPlayerUpdate(final PlayerUpdateEvent playerUpdateEvent) {
        this.a.forEach(gameEventListener -> gameEventListener.onPlayerUpdate(playerUpdateEvent));
    }
    
    @Override
    public final void onSendChatMessage(final ChatEvent chatEvent) {
        this.a.forEach(gameEventListener -> gameEventListener.onSendChatMessage(chatEvent));
    }
    
    @Override
    public final void onPreTabComplete(final TabCompleteEvent tabCompleteEvent) {
        this.a.forEach(gameEventListener -> gameEventListener.onPreTabComplete(tabCompleteEvent));
    }
    
    @Override
    public final void onChunkEvent(final ChunkEvent chunkEvent) {
        final EventState state = chunkEvent.getState();
        final ChunkEvent$Type type = chunkEvent.getType();
        final boolean b = state == EventState.POST && (type == ChunkEvent$Type.POPULATE_FULL || type == ChunkEvent$Type.POPULATE_PARTIAL);
        final amu world = this.a.getPlayerContext().world();
        final boolean b2 = state == EventState.PRE && type == ChunkEvent$Type.UNLOAD && world.B().e(chunkEvent.getX(), chunkEvent.getZ());
        if (b || b2) {
            final v a = this.a.a;
            final Consumer<u> consumer = u -> u.getCachedWorld().queueForPacking(world.a(chunkEvent.getX(), chunkEvent.getZ()));
            final v v = a;
            if (a.a != null) {
                consumer.accept(v.a);
            }
        }
        this.a.forEach(gameEventListener -> gameEventListener.onChunkEvent(chunkEvent));
    }
    
    @Override
    public final void onRenderPass(final RenderEvent renderEvent) {
        this.a.forEach(gameEventListener -> gameEventListener.onRenderPass(renderEvent));
    }
    
    @Override
    public final void onWorldEvent(final WorldEvent worldEvent) {
        final v a = this.a.a;
        if (worldEvent.getState() == EventState.POST) {
            final v v;
            final u a2 = (v = a).a;
            v.a = null;
            if (a2 != null) {
                a2.a();
            }
            if (worldEvent.getWorld() != null) {
                a.a(worldEvent.getWorld().s.q().a());
            }
        }
        this.a.forEach(gameEventListener -> gameEventListener.onWorldEvent(worldEvent));
    }
    
    @Override
    public final void onSendPacket(final PacketEvent packetEvent) {
        this.a.forEach(gameEventListener -> gameEventListener.onSendPacket(packetEvent));
    }
    
    @Override
    public final void onReceivePacket(final PacketEvent packetEvent) {
        this.a.forEach(gameEventListener -> gameEventListener.onReceivePacket(packetEvent));
    }
    
    @Override
    public final void onPlayerRotationMove(final RotationMoveEvent rotationMoveEvent) {
        this.a.forEach(gameEventListener -> gameEventListener.onPlayerRotationMove(rotationMoveEvent));
    }
    
    @Override
    public final void onPlayerSprintState(final SprintStateEvent sprintStateEvent) {
        this.a.forEach(gameEventListener -> gameEventListener.onPlayerSprintState(sprintStateEvent));
    }
    
    @Override
    public final void onBlockInteract(final BlockInteractEvent blockInteractEvent) {
        this.a.forEach(gameEventListener -> gameEventListener.onBlockInteract(blockInteractEvent));
    }
    
    @Override
    public final void onPlayerDeath() {
        this.a.forEach(IGameEventListener::onPlayerDeath);
    }
    
    @Override
    public final void onPathEvent(final PathEvent pathEvent) {
        this.a.forEach(gameEventListener -> gameEventListener.onPathEvent(pathEvent));
    }
    
    @Override
    public final void registerEventListener(final IGameEventListener gameEventListener) {
        this.a.add(gameEventListener);
    }
}
