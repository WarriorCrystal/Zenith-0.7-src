// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.util;

import com.github.lunatrius.schematica.reference.Reference;
import java.util.Collections;
import java.util.List;
import com.github.lunatrius.schematica.client.util.BlockList;
import java.util.Comparator;

public enum ItemStackSortType
{
    NAME_ASC("name", "\u2191", (wrappedItemStackA, wrappedItemStackB) -> {
        nameA = wrappedItemStackA.getItemStackDisplayName();
        nameB = wrappedItemStackB.getItemStackDisplayName();
        return nameA.compareTo(nameB);
    }), 
    NAME_DESC("name", "\u2193", (wrappedItemStackA, wrappedItemStackB) -> {
        nameA2 = wrappedItemStackA.getItemStackDisplayName();
        nameB2 = wrappedItemStackB.getItemStackDisplayName();
        return nameB2.compareTo(nameA2);
    }), 
    SIZE_ASC("amount", "\u2191", (wrappedItemStackA, wrappedItemStackB) -> wrappedItemStackA.total - wrappedItemStackB.total), 
    SIZE_DESC("amount", "\u2193", (wrappedItemStackA, wrappedItemStackB) -> wrappedItemStackB.total - wrappedItemStackA.total);
    
    private final Comparator<BlockList.WrappedItemStack> comparator;
    public final String label;
    public final String glyph;
    
    private ItemStackSortType(final String label, final String glyph, final Comparator<BlockList.WrappedItemStack> comparator) {
        this.label = label;
        this.glyph = glyph;
        this.comparator = comparator;
    }
    
    public void sort(final List<BlockList.WrappedItemStack> blockList) {
        try {
            Collections.sort(blockList, this.comparator);
        }
        catch (Exception e) {
            Reference.logger.error("Could not sort the block list!", (Throwable)e);
        }
    }
    
    public ItemStackSortType next() {
        final ItemStackSortType[] values = values();
        return values[(this.ordinal() + 1) % values.length];
    }
    
    public static ItemStackSortType fromString(final String name) {
        try {
            return valueOf(name);
        }
        catch (Exception ex) {
            return ItemStackSortType.NAME_ASC;
        }
    }
}
