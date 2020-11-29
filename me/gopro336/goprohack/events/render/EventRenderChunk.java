// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.render;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.BlockPos;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventRenderChunk extends MinecraftEvent
{
    public BlockPos BlockPos;
    public RenderChunk RenderChunk;
    
    public EventRenderChunk(final RenderChunk renderChunk, final BlockPos blockPos) {
        this.BlockPos = blockPos;
        this.RenderChunk = renderChunk;
    }
}
