// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.cache;

public interface IWorldData
{
    ICachedWorld getCachedWorld();
    
    IWaypointCollection getWaypoints();
    
    IContainerMemory getContainerMemory();
}
