// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.player;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventPlayerDamageBlock extends MinecraftEvent
{
    private BlockPos BlockPos;
    private EnumFacing Direction;
    
    public EventPlayerDamageBlock(final BlockPos posBlock, final EnumFacing directionFacing) {
        this.BlockPos = posBlock;
        this.setDirection(directionFacing);
    }
    
    public BlockPos getPos() {
        return this.BlockPos;
    }
    
    public EnumFacing getDirection() {
        return this.Direction;
    }
    
    public void setDirection(final EnumFacing direction) {
        this.Direction = direction;
    }
}
