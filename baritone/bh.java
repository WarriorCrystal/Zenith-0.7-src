// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import baritone.api.command.datatypes.BlockById;
import java.util.stream.Stream;
import baritone.api.command.datatypes.ForBlockOptionalMeta;
import baritone.api.utils.BlockOptionalMeta;
import baritone.api.command.datatypes.RelativeGoal;
import baritone.api.pathing.goals.Goal;
import baritone.api.command.datatypes.IDatatypePost;
import baritone.api.command.datatypes.RelativeCoordinate;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class bh extends Command
{
    public bh(final IBaritone baritone) {
        super(baritone, new String[] { "goto" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        if (argConsumer.peekDatatypeOrNull((IDatatypePost<Object, Object>)RelativeCoordinate.INSTANCE) != null) {
            argConsumer.requireMax(3);
            final Goal goalAndPath = argConsumer.getDatatypePost(RelativeGoal.INSTANCE, this.baritone.getPlayerContext().playerFeet());
            this.logDirect(String.format("Going to: %s", goalAndPath.toString()));
            this.baritone.getCustomGoalProcess().setGoalAndPath(goalAndPath);
            return;
        }
        argConsumer.requireMax(1);
        this.baritone.getGetToBlockProcess().getToBlock(argConsumer.getDatatypeFor(ForBlockOptionalMeta.INSTANCE));
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return argConsumer.tabCompleteDatatype(BlockById.INSTANCE);
    }
    
    @Override
    public final String getShortDesc() {
        return "Go to a coordinate or block";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The got command tells Baritone to head towards a given goal or block.", "", "Wherever a coordinate is expected, you can use ~ just like in regular Minecraft commands. Or, you can just use regular numbers.", "", "Usage:", "> goto <block> - Go to a block, wherever it is in the world", "> goto <y> - Go to a Y level", "> goto <x> <z> - Go to an X,Z position", "> goto <x> <y> <z> - Go to an X,Y,Z position");
    }
}
