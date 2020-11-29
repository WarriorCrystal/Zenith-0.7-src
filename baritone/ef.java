// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.goals.GoalGetToBlock;

public final class ef extends GoalGetToBlock
{
    private boolean a;
    private et a;
    
    public ef(final et et, final et a, final boolean a2) {
        super(et);
        this.a = a;
        this.a = a2;
    }
    
    @Override
    public final boolean isInGoal(final int n, final int n2, final int n3) {
        return (n != this.x || n2 != this.y || n3 != this.z) && (n != this.a.p() || n2 != this.a.q() || n3 != this.a.r()) && (this.a || n2 != this.y - 1) && n2 >= this.y - 1 && super.isInGoal(n, n2, n3);
    }
    
    @Override
    public final double heuristic(final int n, final int n2, final int n3) {
        return this.y * 100 + super.heuristic(n, n2, n3);
    }
}
