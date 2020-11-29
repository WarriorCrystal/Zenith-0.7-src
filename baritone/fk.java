// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.utils.IPlayerContext;
import baritone.api.utils.Helper;
import baritone.api.process.IBaritoneProcess;

public abstract class fk implements IBaritoneProcess, Helper
{
    public final a a;
    protected final IPlayerContext a;
    
    public fk(final a a) {
        this.a = a;
        this.a = a.getPlayerContext();
        a.a.registerProcess(this);
    }
    
    @Override
    public boolean isTemporary() {
        return false;
    }
}
