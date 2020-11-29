// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.process.PathingCommandType;
import baritone.api.process.PathingCommand;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.ICustomGoalProcess;

public final class ek extends fk implements ICustomGoalProcess
{
    private Goal a;
    private int a;
    
    public ek(final a a) {
        super(a);
    }
    
    @Override
    public final void setGoal(final Goal a) {
        this.a = a;
        if (this.a == em.a) {
            this.a = em.b;
        }
        if (this.a == em.d) {
            this.a = em.c;
        }
    }
    
    @Override
    public final void path() {
        this.a = em.c;
    }
    
    @Override
    public final Goal getGoal() {
        return this.a;
    }
    
    @Override
    public final boolean isActive() {
        return this.a != em.a;
    }
    
    @Override
    public final PathingCommand onTick(final boolean b, final boolean b2) {
        switch (el.a[this.a - 1]) {
            case 1: {
                return new PathingCommand(this.a, PathingCommandType.CANCEL_AND_SET_GOAL);
            }
            case 2: {
                final PathingCommand pathingCommand = new PathingCommand(this.a, PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH);
                this.a = em.d;
                return pathingCommand;
            }
            case 3: {
                if (b) {
                    this.onLostControl();
                    return new PathingCommand(this.a, PathingCommandType.CANCEL_AND_SET_GOAL);
                }
                if (this.a == null || (this.a.isInGoal(this.a.playerFeet()) && this.a.isInGoal(this.a.a.a()))) {
                    this.onLostControl();
                    if (baritone.a.a().disconnectOnArrival.value) {
                        this.a.world().O();
                    }
                    return new PathingCommand(this.a, PathingCommandType.CANCEL_AND_SET_GOAL);
                }
                return new PathingCommand(this.a, PathingCommandType.SET_GOAL_AND_PATH);
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }
    
    @Override
    public final void onLostControl() {
        this.a = em.a;
        this.a = null;
    }
    
    @Override
    public final String displayName0() {
        return "Custom Goal " + this.a;
    }
}
