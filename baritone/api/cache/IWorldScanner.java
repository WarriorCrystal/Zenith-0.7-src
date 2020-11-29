// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.cache;

import java.util.List;
import baritone.api.utils.BlockOptionalMetaLookup;
import baritone.api.utils.IPlayerContext;

public interface IWorldScanner
{
    List<et> scanChunkRadius(final IPlayerContext p0, final BlockOptionalMetaLookup p1, final int p2, final int p3, final int p4);
    
    default List<et> scanChunkRadius(final IPlayerContext playerContext, final List<aow> list, final int n, final int n2, final int n3) {
        return this.scanChunkRadius(playerContext, new BlockOptionalMetaLookup((aow[])list.toArray(new aow[0])), n, n2, n3);
    }
    
    List<et> scanChunk(final IPlayerContext p0, final BlockOptionalMetaLookup p1, final amn p2, final int p3, final int p4);
    
    default List<et> scanChunk(final IPlayerContext playerContext, final List<aow> list, final amn amn, final int n, final int n2) {
        return this.scanChunk(playerContext, new BlockOptionalMetaLookup(list), amn, n, n2);
    }
    
    int repack(final IPlayerContext p0);
    
    int repack(final IPlayerContext p0, final int p1);
}
