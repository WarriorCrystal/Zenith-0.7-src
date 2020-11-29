// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.pathing.goals;

import baritone.api.BaritoneAPI;
import baritone.api.utils.SettingsUtil;
import baritone.api.utils.BetterBlockPos;

public class GoalXZ implements Goal
{
    private static final double SQRT_2;
    private final int x;
    private final int z;
    
    public GoalXZ(final int x, final int z) {
        this.x = x;
        this.z = z;
    }
    
    public GoalXZ(final BetterBlockPos betterBlockPos) {
        this.x = betterBlockPos.a;
        this.z = betterBlockPos.c;
    }
    
    @Override
    public boolean isInGoal(final int n, final int n2, final int n3) {
        return n == this.x && n3 == this.z;
    }
    
    @Override
    public double heuristic(int n, int n2, final int n3) {
        n -= this.x;
        n2 = n3 - this.z;
        return calculate(n, n2);
    }
    
    @Override
    public String toString() {
        return String.format("GoalXZ{x=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.z));
    }
    
    public static double calculate(final double a, final double a2) {
        final double abs = Math.abs(a);
        final double abs2 = Math.abs(a2);
        double n;
        double n2;
        if (abs < abs2) {
            n = abs2 - abs;
            n2 = abs;
        }
        else {
            n = abs - abs2;
            n2 = abs2;
        }
        return (n2 * GoalXZ.SQRT_2 + n) * BaritoneAPI.getSettings().costHeuristic.value;
    }
    
    public static GoalXZ fromDirection(final bhe bhe, float n, final double n2) {
        n = (float)Math.toRadians(n);
        return new GoalXZ(rk.c(bhe.b - rk.a(n) * n2), rk.c(bhe.d + rk.b(n) * n2));
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getZ() {
        return this.z;
    }
    
    static {
        SQRT_2 = Math.sqrt(2.0);
    }
}
