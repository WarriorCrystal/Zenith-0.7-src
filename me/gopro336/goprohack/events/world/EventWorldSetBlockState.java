// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventWorldSetBlockState extends MinecraftEvent
{
    public BlockPos Pos;
    public IBlockState NewState;
    public int Flags;
    
    public EventWorldSetBlockState(final BlockPos pos, final IBlockState newState, final int flags) {
        this.Pos = pos;
        this.NewState = newState;
        this.Flags = flags;
    }
}
