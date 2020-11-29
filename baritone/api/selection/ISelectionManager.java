// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.selection;

import baritone.api.utils.BetterBlockPos;

public interface ISelectionManager
{
    ISelection addSelection(final ISelection p0);
    
    ISelection addSelection(final BetterBlockPos p0, final BetterBlockPos p1);
    
    ISelection removeSelection(final ISelection p0);
    
    ISelection[] removeAllSelections();
    
    ISelection[] getSelections();
    
    ISelection getOnlySelection();
    
    ISelection getLastSelection();
    
    ISelection expand(final ISelection p0, final fa p1, final int p2);
    
    ISelection contract(final ISelection p0, final fa p1, final int p2);
    
    ISelection shift(final ISelection p0, final fa p1, final int p2);
}
