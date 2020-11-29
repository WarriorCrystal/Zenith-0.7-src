// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.process;

import java.util.function.Function;
import java.util.stream.Stream;
import baritone.api.utils.BlockOptionalMeta;
import baritone.api.utils.BlockOptionalMetaLookup;

public interface IMineProcess extends IBaritoneProcess
{
    void mineByName(final int p0, final String... p1);
    
    void mine(final int p0, final BlockOptionalMetaLookup p1);
    
    default void mine(final BlockOptionalMetaLookup blockOptionalMetaLookup) {
        this.mine(0, blockOptionalMetaLookup);
    }
    
    default void mineByName(final String... array) {
        this.mineByName(0, array);
    }
    
    default void mine(final int n, final BlockOptionalMeta... array) {
        this.mine(n, new BlockOptionalMetaLookup(array));
    }
    
    default void mine(final BlockOptionalMeta... array) {
        this.mine(0, array);
    }
    
    default void mine(final int n2, final aow... values) {
        this.mine(n2, new BlockOptionalMetaLookup((BlockOptionalMeta[])Stream.of(values).map((Function<? super aow, ?>)BlockOptionalMeta::new).toArray(BlockOptionalMeta[]::new)));
    }
    
    default void mine(final aow... array) {
        this.mine(0, array);
    }
    
    default void cancel() {
        this.onLostControl();
    }
}
