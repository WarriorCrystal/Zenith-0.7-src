// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.cache;

import java.util.Map;

public interface IContainerMemory
{
    IRememberedInventory getInventoryByPos(final et p0);
    
    Map<et, IRememberedInventory> getRememberedInventories();
}
