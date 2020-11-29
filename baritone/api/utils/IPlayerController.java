// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

import baritone.api.BaritoneAPI;

public interface IPlayerController
{
    void syncHeldItem();
    
    boolean hasBrokenBlock();
    
    boolean onPlayerDamageBlock(final et p0, final fa p1);
    
    void resetBlockRemoving();
    
    aip windowClick(final int p0, final int p1, final int p2, final afw p3, final aed p4);
    
    ams getGameType();
    
    ud processRightClickBlock(final bud p0, final amu p1, final et p2, final fa p3, final bhe p4, final ub p5);
    
    ud processRightClick(final bud p0, final amu p1, final ub p2);
    
    boolean clickBlock(final et p0, final fa p1);
    
    void setHittingBlock(final boolean p0);
    
    default double getBlockReachDistance() {
        if (this.getGameType().d()) {
            return 5.0;
        }
        return BaritoneAPI.getSettings().blockReachDistance.value;
    }
}
