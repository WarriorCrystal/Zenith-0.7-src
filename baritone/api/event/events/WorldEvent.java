// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.event.events;

import baritone.api.event.events.type.EventState;

public final class WorldEvent
{
    private final bsb world;
    private final EventState state;
    
    public WorldEvent(final bsb world, final EventState state) {
        this.world = world;
        this.state = state;
    }
    
    public final bsb getWorld() {
        return this.world;
    }
    
    public final EventState getState() {
        return this.state;
    }
}
