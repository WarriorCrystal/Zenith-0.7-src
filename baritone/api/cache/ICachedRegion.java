// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.cache;

public interface ICachedRegion extends IBlockTypeAccess
{
    boolean isCached(final int p0, final int p1);
    
    int getX();
    
    int getZ();
}
