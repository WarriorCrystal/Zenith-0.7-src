// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.cache;

import baritone.api.utils.BetterBlockPos;

public interface IWaypoint
{
    String getName();
    
    IWaypoint$Tag getTag();
    
    long getCreationTimestamp();
    
    BetterBlockPos getLocation();
}
