// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.ICustomGoalProcess;
import baritone.api.pathing.goals.GoalInverted;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class bj extends Command
{
    public bj(final IBaritone baritone) {
        super(baritone, new String[] { "invert" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        final ICustomGoalProcess customGoalProcess;
        final Goal goal;
        if ((goal = (customGoalProcess = this.baritone.getCustomGoalProcess()).getGoal()) == null) {
            throw new CommandInvalidStateException("No goal");
        }
        Goal origin;
        if (goal instanceof GoalInverted) {
            origin = ((GoalInverted)goal).origin;
        }
        else {
            origin = new GoalInverted(goal);
        }
        customGoalProcess.setGoalAndPath(origin);
        this.logDirect(String.format("Goal: %s", origin.toString()));
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Run away from the current goal";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The invert command tells Baritone to head away from the current goal rather than towards it.", "", "Usage:", "> invert - Invert the current goal.");
    }
}
