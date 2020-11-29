// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.event.events;

public final class BlockInteractEvent
{
    private final et pos;
    private final BlockInteractEvent$Type type;
    
    public BlockInteractEvent(final et pos, final BlockInteractEvent$Type type) {
        this.pos = pos;
        this.type = type;
    }
    
    public final et getPos() {
        return this.pos;
    }
    
    public final BlockInteractEvent$Type getType() {
        return this.type;
    }
}
