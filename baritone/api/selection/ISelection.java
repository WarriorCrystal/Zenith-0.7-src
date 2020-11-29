// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.selection;

import baritone.api.utils.BetterBlockPos;

public interface ISelection
{
    BetterBlockPos pos1();
    
    BetterBlockPos pos2();
    
    BetterBlockPos min();
    
    BetterBlockPos max();
    
    fq size();
    
    bhb aabb();
    
    ISelection expand(final fa p0, final int p1);
    
    ISelection contract(final fa p0, final int p1);
    
    ISelection shift(final fa p0, final int p1);
}
