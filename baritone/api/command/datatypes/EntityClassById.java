// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

import java.util.function.Function;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;

public enum EntityClassById implements IDatatypeFor<Class<? extends vg>>
{
    INSTANCE;
    
    @Override
    public final Class<? extends vg> get(final IDatatypeContext datatypeContext) {
        final Class clazz;
        if ((clazz = (Class)vi.b.c((Object)new nf(datatypeContext.getConsumer().getString()))) == null) {
            throw new IllegalArgumentException("no entity found by that id");
        }
        return (Class<? extends vg>)clazz;
    }
    
    @Override
    public final Stream<String> tabComplete(final IDatatypeContext datatypeContext) {
        return new TabCompleteHelper().append(vi.a().stream().map(Object::toString)).filterPrefixNamespaced(datatypeContext.getConsumer().getString()).sortAlphabetically().stream();
    }
}
