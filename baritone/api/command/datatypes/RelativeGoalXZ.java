// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

import java.util.stream.Stream;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.utils.BetterBlockPos;
import baritone.api.pathing.goals.GoalXZ;

public enum RelativeGoalXZ implements IDatatypePost<GoalXZ, BetterBlockPos>
{
    INSTANCE;
    
    @Override
    public final GoalXZ apply(final IDatatypeContext datatypeContext, BetterBlockPos origin) {
        if (origin == null) {
            origin = BetterBlockPos.ORIGIN;
        }
        final IArgConsumer consumer = datatypeContext.getConsumer();
        return new GoalXZ(rk.c((double)consumer.getDatatypePost(RelativeCoordinate.INSTANCE, (double)origin.a)), rk.c((double)consumer.getDatatypePost(RelativeCoordinate.INSTANCE, (double)origin.c)));
    }
    
    @Override
    public final Stream<String> tabComplete(final IDatatypeContext datatypeContext) {
        final IArgConsumer consumer;
        if ((consumer = datatypeContext.getConsumer()).hasAtMost(2)) {
            return consumer.tabCompleteDatatype(RelativeCoordinate.INSTANCE);
        }
        return Stream.empty();
    }
}
