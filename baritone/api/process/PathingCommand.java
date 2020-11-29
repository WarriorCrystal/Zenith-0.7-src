// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.process;

import java.util.Objects;
import baritone.api.pathing.goals.Goal;

public class PathingCommand
{
    public final Goal goal;
    public final PathingCommandType commandType;
    
    public PathingCommand(final Goal goal, final PathingCommandType pathingCommandType) {
        Objects.requireNonNull(pathingCommandType);
        this.goal = goal;
        this.commandType = pathingCommandType;
    }
    
    @Override
    public String toString() {
        return this.commandType + " " + this.goal;
    }
}
