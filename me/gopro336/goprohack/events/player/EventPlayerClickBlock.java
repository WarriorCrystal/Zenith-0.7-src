// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.player;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventPlayerClickBlock extends MinecraftEvent
{
    public BlockPos Location;
    public EnumFacing Facing;
    
    public EventPlayerClickBlock(final BlockPos loc, final EnumFacing face) {
        this.Location = loc;
        this.Facing = face;
    }
}
