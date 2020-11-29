// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.event.events;

import java.util.function.BiFunction;
import baritone.api.event.events.type.EventState;

public final class TickEvent
{
    private static int overallTickCount;
    private final EventState state;
    private final TickEvent$Type type;
    private final int count;
    
    public TickEvent(final EventState state, final TickEvent$Type type, final int count) {
        this.state = state;
        this.type = type;
        this.count = count;
    }
    
    public final int getCount() {
        return this.count;
    }
    
    public final TickEvent$Type getType() {
        return this.type;
    }
    
    public final EventState getState() {
        return this.state;
    }
    
    public static synchronized BiFunction<EventState, TickEvent$Type, TickEvent> createNextProvider() {
        TickEvent.overallTickCount++;
        final int n;
        return (BiFunction<EventState, TickEvent$Type, TickEvent>)((eventState, tickEvent$Type) -> new TickEvent(eventState, tickEvent$Type, n));
    }
}
