// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

import java.util.stream.Stream;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.pathing.goals.GoalYLevel;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.utils.BetterBlockPos;
import baritone.api.pathing.goals.Goal;

public enum RelativeGoal implements IDatatypePost<Goal, BetterBlockPos>
{
    INSTANCE;
    
    @Override
    public final Goal apply(final IDatatypeContext datatypeContext, BetterBlockPos origin) {
        if (origin == null) {
            origin = BetterBlockPos.ORIGIN;
        }
        final IArgConsumer consumer;
        final GoalBlock goalBlock;
        if ((goalBlock = (GoalBlock)(consumer = datatypeContext.getConsumer()).peekDatatypePostOrNull(RelativeGoalBlock.INSTANCE, origin)) != null) {
            return goalBlock;
        }
        final GoalXZ goalXZ;
        if ((goalXZ = consumer.peekDatatypePostOrNull(RelativeGoalXZ.INSTANCE, origin)) != null) {
            return goalXZ;
        }
        final GoalYLevel goalYLevel;
        if ((goalYLevel = consumer.peekDatatypePostOrNull(RelativeGoalYLevel.INSTANCE, origin)) != null) {
            return goalYLevel;
        }
        return new GoalBlock(origin);
    }
    
    @Override
    public final Stream<String> tabComplete(final IDatatypeContext datatypeContext) {
        return datatypeContext.getConsumer().tabCompleteDatatype(RelativeCoordinate.INSTANCE);
    }
}
