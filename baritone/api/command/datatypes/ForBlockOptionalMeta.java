// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

import java.util.stream.Stream;
import baritone.api.utils.BlockOptionalMeta;

public enum ForBlockOptionalMeta implements IDatatypeFor<BlockOptionalMeta>
{
    INSTANCE;
    
    @Override
    public final BlockOptionalMeta get(final IDatatypeContext datatypeContext) {
        return new BlockOptionalMeta(datatypeContext.getConsumer().getString());
    }
    
    @Override
    public final Stream<String> tabComplete(final IDatatypeContext datatypeContext) {
        return datatypeContext.getConsumer().tabCompleteDatatype(BlockById.INSTANCE);
    }
}
