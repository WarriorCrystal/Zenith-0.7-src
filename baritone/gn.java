// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.utils.IPlayerController;
import baritone.api.utils.Helper;

public enum gn implements Helper, IPlayerController
{
    a;
    
    @Override
    public final void syncHeldItem() {
        ((gg)gn.mc.c).callSyncCurrentPlayItem();
    }
    
    @Override
    public final boolean hasBrokenBlock() {
        return ((gg)gn.mc.c).getCurrentBlock().q() == -1;
    }
    
    @Override
    public final boolean onPlayerDamageBlock(final et et, final fa fa) {
        return gn.mc.c.b(et, fa);
    }
    
    @Override
    public final void resetBlockRemoving() {
        gn.mc.c.c();
    }
    
    @Override
    public final aip windowClick(final int n, final int n2, final int n3, final afw afw, final aed aed) {
        return gn.mc.c.a(n, n2, n3, afw, aed);
    }
    
    @Override
    public final ams getGameType() {
        return gn.mc.c.l();
    }
    
    @Override
    public final ud processRightClickBlock(final bud bud, final amu amu, final et et, final fa fa, final bhe bhe, final ub ub) {
        return gn.mc.c.a(bud, (bsb)amu, et, fa, bhe, ub);
    }
    
    @Override
    public final ud processRightClick(final bud bud, final amu amu, final ub ub) {
        return gn.mc.c.a((aed)bud, amu, ub);
    }
    
    @Override
    public final boolean clickBlock(final et et, final fa fa) {
        return gn.mc.c.a(et, fa);
    }
    
    @Override
    public final void setHittingBlock(final boolean isHittingBlock) {
        ((gg)gn.mc.c).setIsHittingBlock(isHittingBlock);
    }
}
