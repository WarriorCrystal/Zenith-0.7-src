// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.cache;

import java.util.Set;

public interface IWaypointCollection
{
    void addWaypoint(final IWaypoint p0);
    
    void removeWaypoint(final IWaypoint p0);
    
    IWaypoint getMostRecentByTag(final IWaypoint$Tag p0);
    
    Set<IWaypoint> getByTag(final IWaypoint$Tag p0);
    
    Set<IWaypoint> getAllWaypoints();
}
