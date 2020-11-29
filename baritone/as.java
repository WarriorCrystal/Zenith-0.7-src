// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.goals.Goal;
import baritone.api.process.PathingCommandType;
import baritone.api.process.PathingCommand;
import baritone.api.process.IBaritoneProcess;

final class as implements IBaritoneProcess
{
    private /* synthetic */ boolean[] a;
    private /* synthetic */ ar a;
    
    as(final ar a, final boolean[] a2) {
        this.a = a;
        this.a = a2;
    }
    
    @Override
    public final boolean isActive() {
        return this.a[0];
    }
    
    @Override
    public final PathingCommand onTick(final boolean b, final boolean b2) {
        return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
    }
    
    @Override
    public final boolean isTemporary() {
        return true;
    }
    
    @Override
    public final void onLostControl() {
    }
    
    @Override
    public final double priority() {
        return 0.0;
    }
    
    @Override
    public final String displayName0() {
        return "Pause/Resume Commands";
    }
}
