// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.event.events.TickEvent$Type;
import baritone.api.event.events.TickEvent;
import baritone.api.event.listener.AbstractGameEventListener;

final class fw implements AbstractGameEventListener
{
    private /* synthetic */ fv a;
    
    fw(final fv a) {
        this.a = a;
    }
    
    @Override
    public final void onTick(final TickEvent tickEvent) {
        if (tickEvent.getType() == TickEvent$Type.IN) {
            fv.a(this.a);
        }
    }
}
