// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import baritone.api.command.datatypes.RelativeGoalXZ;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class ax extends Command
{
    public ax(final IBaritone baritone) {
        super(baritone, new String[] { "explore" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        if (argConsumer.hasAny()) {
            argConsumer.requireExactly(2);
        }
        else {
            argConsumer.requireMax(0);
        }
        final GoalXZ goalXZ = argConsumer.hasAny() ? argConsumer.getDatatypePost(RelativeGoalXZ.INSTANCE, this.ctx.playerFeet()) : new GoalXZ(this.ctx.playerFeet());
        this.baritone.getExploreProcess().explore(goalXZ.getX(), goalXZ.getZ());
        this.logDirect(String.format("Exploring from %s", goalXZ.toString()));
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        if (argConsumer.hasAtMost(2)) {
            return argConsumer.tabCompleteDatatype(RelativeGoalXZ.INSTANCE);
        }
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Explore things";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Tell Baritone to explore randomly. If you used explorefilter before this, it will be applied.", "", "Usage:", "> explore - Explore from your current position.", "> explore <x> <z> - Explore from the specified X and Z position.");
    }
}
