// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.pathing.goals;

public class GoalInverted implements Goal
{
    public final Goal origin;
    
    public GoalInverted(final Goal origin) {
        this.origin = origin;
    }
    
    @Override
    public boolean isInGoal(final int n, final int n2, final int n3) {
        return false;
    }
    
    @Override
    public double heuristic(final int n, final int n2, final int n3) {
        return -this.origin.heuristic(n, n2, n3);
    }
    
    @Override
    public String toString() {
        return String.format("GoalInverted{%s}", this.origin.toString());
    }
}
