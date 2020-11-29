// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.pathing.goals;

import baritone.api.utils.SettingsUtil;
import baritone.api.pathing.movement.ActionCosts;

public class GoalYLevel implements Goal, ActionCosts
{
    public final int level;
    
    public GoalYLevel(final int level) {
        this.level = level;
    }
    
    @Override
    public boolean isInGoal(final int n, final int n2, final int n3) {
        return n2 == this.level;
    }
    
    @Override
    public double heuristic(final int n, final int n2, final int n3) {
        return calculate(this.level, n2);
    }
    
    public static double calculate(final int n, final int n2) {
        if (n2 > n) {
            return GoalYLevel.FALL_N_BLOCKS_COST[2] / 2.0 * (n2 - n);
        }
        if (n2 < n) {
            return (n - n2) * GoalYLevel.JUMP_ONE_BLOCK_COST;
        }
        return 0.0;
    }
    
    @Override
    public String toString() {
        return String.format("GoalYLevel{y=%s}", SettingsUtil.maybeCensor(this.level));
    }
}
