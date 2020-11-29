// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.process;

import baritone.api.pathing.goals.Goal;

public interface ICustomGoalProcess extends IBaritoneProcess
{
    void setGoal(final Goal p0);
    
    void path();
    
    Goal getGoal();
    
    default void setGoalAndPath(final Goal goal) {
        this.setGoal(goal);
        this.path();
    }
}
