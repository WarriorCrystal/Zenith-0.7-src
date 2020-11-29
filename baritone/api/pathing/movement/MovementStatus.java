// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.pathing.movement;

public enum MovementStatus
{
    PREPPING(false), 
    WAITING(false), 
    RUNNING(false), 
    SUCCESS(true), 
    UNREACHABLE(true), 
    FAILED(true), 
    CANCELED(true);
    
    private final boolean complete;
    
    private MovementStatus(final boolean complete) {
        this.complete = complete;
    }
    
    public final boolean isComplete() {
        return this.complete;
    }
}
