// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

import java.util.stream.Stream;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.utils.BetterBlockPos;

public enum RelativeBlockPos implements IDatatypePost<BetterBlockPos, BetterBlockPos>
{
    INSTANCE;
    
    @Override
    public final BetterBlockPos apply(final IDatatypeContext datatypeContext, BetterBlockPos origin) {
        if (origin == null) {
            origin = BetterBlockPos.ORIGIN;
        }
        final IArgConsumer consumer = datatypeContext.getConsumer();
        return new BetterBlockPos((double)consumer.getDatatypePost(RelativeCoordinate.INSTANCE, (double)origin.a), (double)consumer.getDatatypePost(RelativeCoordinate.INSTANCE, (double)origin.b), (double)consumer.getDatatypePost(RelativeCoordinate.INSTANCE, (double)origin.c));
    }
    
    @Override
    public final Stream<String> tabComplete(final IDatatypeContext datatypeContext) {
        final IArgConsumer consumer;
        if ((consumer = datatypeContext.getConsumer()).hasAny() && !consumer.has(4)) {
            while (consumer.has(2) && consumer.peekDatatypeOrNull((IDatatypePost<Object, Object>)RelativeCoordinate.INSTANCE) != null) {
                consumer.get();
            }
            return consumer.tabCompleteDatatype(RelativeCoordinate.INSTANCE);
        }
        return Stream.empty();
    }
}
