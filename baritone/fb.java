// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.goals.GoalRunAway;

final class fb extends GoalRunAway
{
    private /* synthetic */ fa a;
    
    fb(final fa a, final et... array) {
        this.a = a;
        super(1.0, array);
    }
    
    @Override
    public final boolean isInGoal(final int n, final int n2, final int n3) {
        return false;
    }
}
