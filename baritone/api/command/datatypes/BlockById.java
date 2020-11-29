// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

import java.util.function.Function;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;

public enum BlockById implements IDatatypeFor<aow>
{
    INSTANCE;
    
    @Override
    public final aow get(final IDatatypeContext datatypeContext) {
        final aow aow;
        if ((aow = (aow)aow.h.c((Object)new nf(datatypeContext.getConsumer().getString()))) == aox.a) {
            throw new IllegalArgumentException("no block found by that id");
        }
        return aow;
    }
    
    @Override
    public final Stream<String> tabComplete(final IDatatypeContext datatypeContext) {
        return new TabCompleteHelper().append(aow.h.c().stream().map(Object::toString)).filterPrefixNamespaced(datatypeContext.getConsumer().getString()).sortAlphabetically().stream();
    }
}
