// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.event.events;

public final class RenderEvent
{
    private final float partialTicks;
    
    public RenderEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public final float getPartialTicks() {
        return this.partialTicks;
    }
}
