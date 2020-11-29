// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.process.IBaritoneProcess;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class ar
{
    public Command a;
    public Command b;
    public Command c;
    public Command d;
    
    public ar(final IBaritone baritone) {
        final boolean[] array = { false };
        baritone.getPathingControlManager().registerProcess(new as(this, array));
        this.a = new at(this, baritone, new String[] { "pause" }, array);
        this.b = new au(this, baritone, new String[] { "resume" }, array);
        this.c = new av(this, baritone, new String[] { "paused" }, array);
        this.d = new aw(this, baritone, new String[] { "cancel", "stop" }, array);
    }
}
