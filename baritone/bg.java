// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.List;
import baritone.api.command.datatypes.IDatatypePost;
import baritone.api.command.datatypes.RelativeCoordinate;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;
import baritone.api.process.ICustomGoalProcess;
import baritone.api.command.datatypes.RelativeGoal;
import baritone.api.pathing.goals.Goal;
import java.util.Arrays;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class bg extends Command
{
    public bg(final IBaritone baritone) {
        super(baritone, new String[] { "goal" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        final ICustomGoalProcess customGoalProcess = this.baritone.getCustomGoalProcess();
        if (!argConsumer.hasAny() || !Arrays.asList("reset", "clear", "none").contains(argConsumer.peekString())) {
            argConsumer.requireMax(3);
            final Goal goal = argConsumer.getDatatypePost(RelativeGoal.INSTANCE, this.baritone.getPlayerContext().playerFeet());
            customGoalProcess.setGoal(goal);
            this.logDirect(String.format("Goal: %s", goal.toString()));
            return;
        }
        argConsumer.requireMax(1);
        if (customGoalProcess.getGoal() != null) {
            customGoalProcess.setGoal(null);
            this.logDirect("Cleared goal");
            return;
        }
        this.logDirect("There was no goal to clear");
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        final TabCompleteHelper tabCompleteHelper = new TabCompleteHelper();
        if (argConsumer.hasExactlyOne()) {
            tabCompleteHelper.append("reset", "clear", "none", "~");
        }
        else if (argConsumer.hasAtMost(3)) {
            while (argConsumer.has(2) && argConsumer.peekDatatypeOrNull((IDatatypePost<Object, Object>)RelativeCoordinate.INSTANCE) != null) {
                argConsumer.get();
                if (!argConsumer.has(2)) {
                    tabCompleteHelper.append("~");
                }
            }
        }
        return tabCompleteHelper.filterPrefix(argConsumer.getString()).stream();
    }
    
    @Override
    public final String getShortDesc() {
        return "Set or clear the goal";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The goal command allows you to set or clear Baritone's goal.", "", "Wherever a coordinate is expected, you can use ~ just like in regular Minecraft commands. Or, you can just use regular numbers.", "", "Usage:", "> goal - Set the goal to your current position", "> goal <reset/clear/none> - Erase the goal", "> goal <y> - Set the goal to a Y level", "> goal <x> <z> - Set the goal to an X,Z position", "> goal <x> <y> <z> - Set the goal to an X,Y,Z position");
    }
}
