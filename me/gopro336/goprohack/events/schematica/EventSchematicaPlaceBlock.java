// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.schematica;

import net.minecraft.util.math.BlockPos;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventSchematicaPlaceBlock extends MinecraftEvent
{
    public BlockPos Pos;
    
    public EventSchematicaPlaceBlock(final BlockPos p_Pos) {
        this.Pos = p_Pos;
    }
}
