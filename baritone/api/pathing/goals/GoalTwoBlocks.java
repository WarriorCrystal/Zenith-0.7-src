// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.pathing.goals;

import baritone.api.utils.SettingsUtil;
import baritone.api.utils.interfaces.IGoalRenderPos;

public class GoalTwoBlocks implements Goal, IGoalRenderPos
{
    protected final int x;
    protected final int y;
    protected final int z;
    
    public GoalTwoBlocks(final et et) {
        this(et.p(), et.q(), et.r());
    }
    
    public GoalTwoBlocks(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public boolean isInGoal(final int n, final int n2, final int n3) {
        return n == this.x && (n2 == this.y || n2 == this.y - 1) && n3 == this.z;
    }
    
    @Override
    public double heuristic(int n, int n2, int n3) {
        n -= this.x;
        n2 -= this.y;
        n3 -= this.z;
        return GoalBlock.calculate(n, (n2 < 0) ? (n2 + 1) : n2, n3);
    }
    
    @Override
    public et getGoalPos() {
        return new et(this.x, this.y, this.z);
    }
    
    @Override
    public String toString() {
        return String.format("GoalTwoBlocks{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.y), SettingsUtil.maybeCensor(this.z));
    }
}
