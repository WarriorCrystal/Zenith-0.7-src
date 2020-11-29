// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.schematica;

import net.minecraft.util.math.BlockPos;
import net.minecraft.item.Item;

public class EventSchematicaPlaceBlockFull extends EventSchematicaPlaceBlock
{
    public boolean Result;
    public Item ItemStack;
    
    public EventSchematicaPlaceBlockFull(final BlockPos p_Pos, Item itemStack) {
        super(p_Pos);
        this.Result = true;
        itemStack = this.ItemStack;
    }
    
    public boolean GetResult() {
        return this.Result;
    }
}
