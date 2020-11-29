// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.goals.GoalBlock;

public final class eh extends GoalBlock
{
    public eh(final et et) {
        super(et.a());
    }
    
    @Override
    public final double heuristic(final int n, final int n2, final int n3) {
        return this.y * 100 + super.heuristic(n, n2, n3);
    }
}
