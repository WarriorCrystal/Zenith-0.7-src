// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.pathing.goals;

import baritone.api.utils.SettingsUtil;
import baritone.api.utils.interfaces.IGoalRenderPos;

public class GoalBlock implements Goal, IGoalRenderPos
{
    public final int x;
    public final int y;
    public final int z;
    
    public GoalBlock(final et et) {
        this(et.p(), et.q(), et.r());
    }
    
    public GoalBlock(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public boolean isInGoal(final int n, final int n2, final int n3) {
        return n == this.x && n2 == this.y && n3 == this.z;
    }
    
    @Override
    public double heuristic(int n, int n2, int n3) {
        n -= this.x;
        n2 -= this.y;
        n3 -= this.z;
        return calculate(n, n2, n3);
    }
    
    @Override
    public String toString() {
        return String.format("GoalBlock{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.y), SettingsUtil.maybeCensor(this.z));
    }
    
    @Override
    public et getGoalPos() {
        return new et(this.x, this.y, this.z);
    }
    
    public static double calculate(final double n, final int n2, final double n3) {
        return 0.0 + GoalYLevel.calculate(n2, 0) + GoalXZ.calculate(n, n3);
    }
}
