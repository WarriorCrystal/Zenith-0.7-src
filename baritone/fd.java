// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.goals.GoalRunAway;

final class fd extends GoalRunAway
{
    private /* synthetic */ fc a;
    
    fd(final fc a, final Integer n, final et... array) {
        this.a = a;
        super(1.0, n, array);
    }
    
    @Override
    public final boolean isInGoal(final int n, final int n2, final int n3) {
        return false;
    }
}
