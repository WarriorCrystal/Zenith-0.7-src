// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalAxis;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class ak extends Command
{
    public ak(final IBaritone baritone) {
        super(baritone, new String[] { "axis", "highway" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        final GoalAxis goal = new GoalAxis();
        this.baritone.getCustomGoalProcess().setGoal(goal);
        this.logDirect(String.format("Goal: %s", goal.toString()));
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Set a goal to the axes";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The axis command sets a goal that tells Baritone to head towards the nearest axis. That is, X=0 or Z=0.", "", "Usage:", "> axis");
    }
}
