// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.event.events;

import baritone.api.event.events.type.Cancellable;

public class TabCompleteEvent extends Cancellable
{
    public final String prefix;
    public String[] completions;
    
    public TabCompleteEvent(final String prefix) {
        this.prefix = prefix;
        this.completions = null;
    }
}
