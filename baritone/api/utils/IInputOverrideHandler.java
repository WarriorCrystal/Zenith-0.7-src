// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

import baritone.api.utils.input.Input;
import baritone.api.behavior.IBehavior;

public interface IInputOverrideHandler extends IBehavior
{
    boolean isInputForcedDown(final Input p0);
    
    void setInputForceState(final Input p0, final boolean p1);
    
    void clearAllKeys();
}
