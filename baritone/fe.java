// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalTwoBlocks;

final class fe extends GoalTwoBlocks
{
    public fe(final et et) {
        super(et);
    }
    
    @Override
    public final boolean isInGoal(final int n, final int n2, final int n3) {
        return n == this.x && (n2 == this.y || n2 == this.y - 1 || n2 == this.y - 2) && n3 == this.z;
    }
    
    @Override
    public final double heuristic(int n, int n2, int n3) {
        n -= this.x;
        n2 -= this.y;
        n3 -= this.z;
        return GoalBlock.calculate(n, (n2 < -1) ? (n2 + 2) : ((n2 == -1) ? 0 : n2), n3);
    }
}
