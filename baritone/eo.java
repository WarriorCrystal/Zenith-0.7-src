// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.goals.GoalYLevel;
import baritone.api.pathing.goals.GoalXZ;

final class eo extends GoalXZ
{
    eo(final int n, final int n2) {
        super(n, n2);
    }
    
    @Override
    public final double heuristic(final int n, final int n2, final int n3) {
        return super.heuristic(n, n2, n3) + GoalYLevel.calculate(a.a().exploreMaintainY.value, n2);
    }
}
