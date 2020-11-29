// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class bx extends Command
{
    public bx(final IBaritone baritone) {
        super(baritone, new String[] { "thisway", "forward" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireExactly(1);
        final GoalXZ fromDirection = GoalXZ.fromDirection(this.ctx.playerFeetAsVec(), this.ctx.player().aP, argConsumer.getAs(Double.class));
        this.baritone.getCustomGoalProcess().setGoal(fromDirection);
        this.logDirect(String.format("Goal: %s", fromDirection));
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Travel in your current direction";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Creates a GoalXZ some amount of blocks in the direction you're currently looking", "", "Usage:", "> thisway <distance> - makes a GoalXZ distance blocks in front of you");
    }
}
