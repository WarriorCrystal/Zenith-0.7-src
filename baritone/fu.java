// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.process.PathingCommandType;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.PathingCommand;

public final class fu extends PathingCommand
{
    public final cj a;
    
    public fu(final Goal goal, final PathingCommandType pathingCommandType, final cj a) {
        super(goal, pathingCommandType);
        this.a = a;
    }
}
