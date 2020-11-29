// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

import java.util.List;
import java.util.function.Function;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;

public enum NearbyPlayer implements IDatatypeFor<aed>
{
    INSTANCE;
    
    @Override
    public final aed get(final IDatatypeContext datatypeContext) {
        return getPlayers(datatypeContext).stream().filter(aed -> aed.h_().equalsIgnoreCase(datatypeContext.getConsumer().getString())).findFirst().orElse(null);
    }
    
    @Override
    public final Stream<String> tabComplete(final IDatatypeContext datatypeContext) {
        return new TabCompleteHelper().append(getPlayers(datatypeContext).stream().map((Function<? super Object, ? extends String>)aed::h_)).filterPrefix(datatypeContext.getConsumer().getString()).sortAlphabetically().stream();
    }
    
    private static List<aed> getPlayers(final IDatatypeContext datatypeContext) {
        return (List<aed>)datatypeContext.getBaritone().getPlayerContext().world().i;
    }
}
