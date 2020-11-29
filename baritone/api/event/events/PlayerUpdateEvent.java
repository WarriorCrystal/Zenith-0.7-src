// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.event.events;

import baritone.api.event.events.type.EventState;

public final class PlayerUpdateEvent
{
    private final EventState state;
    
    public PlayerUpdateEvent(final EventState state) {
        this.state = state;
    }
    
    public final EventState getState() {
        return this.state;
    }
}
