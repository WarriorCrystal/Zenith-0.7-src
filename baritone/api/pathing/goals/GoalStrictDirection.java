// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.pathing.goals;

import baritone.api.utils.SettingsUtil;

public class GoalStrictDirection implements Goal
{
    public final int x;
    public final int y;
    public final int z;
    public final int dx;
    public final int dz;
    
    public GoalStrictDirection(final et et, final fa obj) {
        this.x = et.p();
        this.y = et.q();
        this.z = et.r();
        this.dx = obj.g();
        this.dz = obj.i();
        if (this.dx == 0 && this.dz == 0) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
    }
    
    @Override
    public boolean isInGoal(final int n, final int n2, final int n3) {
        return false;
    }
    
    @Override
    public double heuristic(int n, int abs, final int n2) {
        final int n3 = (n - this.x) * this.dx + (n2 - this.z) * this.dz;
        n = Math.abs((n - this.x) * this.dz) + Math.abs((n2 - this.z) * this.dx);
        abs = Math.abs(abs - this.y);
        return -n3 * 100 + (double)(n * 1000) + abs * 1000;
    }
    
    @Override
    public String toString() {
        return String.format("GoalStrictDirection{x=%s, y=%s, z=%s, dx=%s, dz=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.y), SettingsUtil.maybeCensor(this.z), SettingsUtil.maybeCensor(this.dx), SettingsUtil.maybeCensor(this.dz));
    }
}
