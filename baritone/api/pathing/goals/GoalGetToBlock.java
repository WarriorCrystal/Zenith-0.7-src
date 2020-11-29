// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.pathing.goals;

import baritone.api.utils.SettingsUtil;
import baritone.api.utils.interfaces.IGoalRenderPos;

public class GoalGetToBlock implements Goal, IGoalRenderPos
{
    public final int x;
    public final int y;
    public final int z;
    
    public GoalGetToBlock(final et et) {
        this.x = et.p();
        this.y = et.q();
        this.z = et.r();
    }
    
    @Override
    public et getGoalPos() {
        return new et(this.x, this.y, this.z);
    }
    
    @Override
    public boolean isInGoal(int a, int n, int a2) {
        a -= this.x;
        n -= this.y;
        a2 -= this.z;
        return Math.abs(a) + Math.abs((n < 0) ? (n + 1) : n) + Math.abs(a2) <= 1;
    }
    
    @Override
    public double heuristic(int n, int n2, int n3) {
        n -= this.x;
        n2 -= this.y;
        n3 -= this.z;
        return GoalBlock.calculate(n, (n2 < 0) ? (n2 + 1) : n2, n3);
    }
    
    @Override
    public String toString() {
        return String.format("GoalGetToBlock{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.y), SettingsUtil.maybeCensor(this.z));
    }
}
