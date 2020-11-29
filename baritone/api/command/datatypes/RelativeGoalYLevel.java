// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

import baritone.api.command.argument.IArgConsumer;
import java.util.stream.Stream;
import baritone.api.utils.BetterBlockPos;
import baritone.api.pathing.goals.GoalYLevel;

public enum RelativeGoalYLevel implements IDatatypePost<GoalYLevel, BetterBlockPos>
{
    INSTANCE;
    
    @Override
    public final GoalYLevel apply(final IDatatypeContext datatypeContext, BetterBlockPos origin) {
        if (origin == null) {
            origin = BetterBlockPos.ORIGIN;
        }
        return new GoalYLevel(rk.c((double)datatypeContext.getConsumer().getDatatypePost(RelativeCoordinate.INSTANCE, (double)origin.b)));
    }
    
    @Override
    public final Stream<String> tabComplete(final IDatatypeContext datatypeContext) {
        final IArgConsumer consumer;
        if ((consumer = datatypeContext.getConsumer()).hasAtMost(1)) {
            return consumer.tabCompleteDatatype(RelativeCoordinate.INSTANCE);
        }
        return Stream.empty();
    }
}
