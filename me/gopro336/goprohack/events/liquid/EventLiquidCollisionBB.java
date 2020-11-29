// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.liquid;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventLiquidCollisionBB extends MinecraftEvent
{
    private AxisAlignedBB boundingBox;
    private BlockPos blockPos;
    
    public EventLiquidCollisionBB() {
    }
    
    public EventLiquidCollisionBB(final BlockPos blockPos) {
        this.blockPos = blockPos;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    public BlockPos getBlockPos() {
        return this.blockPos;
    }
    
    public void setBlockPos(final BlockPos blockPos) {
        this.blockPos = blockPos;
    }
}
