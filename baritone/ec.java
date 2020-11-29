// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.goals.GoalGetToBlock;

final class ec extends GoalGetToBlock
{
    private /* synthetic */ ea a;
    
    ec(final ea a, final et et) {
        this.a = a;
        super(et);
    }
    
    @Override
    public final boolean isInGoal(final int n, final int n2, final int n3) {
        return n2 <= this.y && (n != this.x || n2 != this.y || n3 != this.z) && super.isInGoal(n, n2, n3);
    }
}
