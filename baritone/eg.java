// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.goals.GoalGetToBlock;

public final class eg extends GoalGetToBlock
{
    public eg(final et et) {
        super(et);
    }
    
    @Override
    public final boolean isInGoal(final int n, final int n2, final int n3) {
        return n2 <= this.y && super.isInGoal(n, n2, n3);
    }
}
