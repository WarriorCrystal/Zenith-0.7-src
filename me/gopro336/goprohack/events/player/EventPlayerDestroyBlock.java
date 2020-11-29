// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.player;

import net.minecraft.util.math.BlockPos;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventPlayerDestroyBlock extends MinecraftEvent
{
    public BlockPos Location;
    
    public EventPlayerDestroyBlock(final BlockPos loc) {
        this.Location = loc;
    }
}
