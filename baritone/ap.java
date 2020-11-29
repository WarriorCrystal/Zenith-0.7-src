// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class ap extends Command
{
    public ap(final IBaritone baritone) {
        super(baritone, new String[] { "come" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        final vg aa;
        if ((aa = ap.mc.aa()) == null) {
            throw new CommandInvalidStateException("render view entity is null");
        }
        this.baritone.getCustomGoalProcess().setGoalAndPath(new GoalBlock(new et(aa)));
        this.logDirect("Coming");
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Start heading towards your camera";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The come command tells Baritone to head towards your camera.", "", "This can be useful in hacked clients where freecam doesn't move your player position.", "", "Usage:", "> come");
    }
}
