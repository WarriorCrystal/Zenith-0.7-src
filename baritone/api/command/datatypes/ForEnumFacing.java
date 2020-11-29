// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

import java.util.function.Function;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;
import java.util.Locale;

public enum ForEnumFacing implements IDatatypeFor<fa>
{
    INSTANCE;
    
    @Override
    public final fa get(final IDatatypeContext datatypeContext) {
        return fa.valueOf(datatypeContext.getConsumer().getString().toUpperCase(Locale.US));
    }
    
    @Override
    public final Stream<String> tabComplete(final IDatatypeContext datatypeContext) {
        return new TabCompleteHelper().append(Stream.of(fa.values()).map((Function<? super fa, ?>)fa::m).map((Function<? super Object, ? extends String>)String::toLowerCase)).filterPrefix(datatypeContext.getConsumer().getString()).stream();
    }
}
