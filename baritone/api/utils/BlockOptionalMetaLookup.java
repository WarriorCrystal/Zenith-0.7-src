// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class BlockOptionalMetaLookup
{
    private final BlockOptionalMeta[] boms;
    
    public BlockOptionalMetaLookup(final BlockOptionalMeta... boms) {
        this.boms = boms;
    }
    
    public BlockOptionalMetaLookup(final aow... values) {
        this.boms = Stream.of(values).map((Function<? super aow, ?>)BlockOptionalMeta::new).toArray(BlockOptionalMeta[]::new);
    }
    
    public BlockOptionalMetaLookup(final List<aow> list) {
        this.boms = list.stream().map((Function<? super Object, ?>)BlockOptionalMeta::new).toArray(BlockOptionalMeta[]::new);
    }
    
    public BlockOptionalMetaLookup(final String... values) {
        this.boms = Stream.of(values).map((Function<? super String, ?>)BlockOptionalMeta::new).toArray(BlockOptionalMeta[]::new);
    }
    
    public boolean has(final aow aow) {
        BlockOptionalMeta[] boms;
        for (int length = (boms = this.boms).length, i = 0; i < length; ++i) {
            if (boms[i].getBlock() == aow) {
                return true;
            }
        }
        return false;
    }
    
    public boolean has(final awt awt) {
        BlockOptionalMeta[] boms;
        for (int length = (boms = this.boms).length, i = 0; i < length; ++i) {
            if (boms[i].matches(awt)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean has(final aip aip) {
        BlockOptionalMeta[] boms;
        for (int length = (boms = this.boms).length, i = 0; i < length; ++i) {
            if (boms[i].matches(aip)) {
                return true;
            }
        }
        return false;
    }
    
    public List<BlockOptionalMeta> blocks() {
        return Arrays.asList(this.boms);
    }
    
    @Override
    public String toString() {
        return String.format("BlockOptionalMetaLookup{%s}", Arrays.toString(this.boms));
    }
}
