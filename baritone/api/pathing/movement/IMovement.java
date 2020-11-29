// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.pathing.movement;

import baritone.api.utils.BetterBlockPos;

public interface IMovement
{
    double getCost();
    
    MovementStatus update();
    
    void reset();
    
    void resetBlockCache();
    
    boolean safeToCancel();
    
    boolean calculatedWhileLoaded();
    
    BetterBlockPos getSrc();
    
    BetterBlockPos getDest();
    
    et getDirection();
}
