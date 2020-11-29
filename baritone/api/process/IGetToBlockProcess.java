// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.process;

import baritone.api.utils.BlockOptionalMeta;

public interface IGetToBlockProcess extends IBaritoneProcess
{
    void getToBlock(final BlockOptionalMeta p0);
    
    default void getToBlock(final aow aow) {
        this.getToBlock(new BlockOptionalMeta(aow));
    }
    
    boolean blacklistClosest();
}
