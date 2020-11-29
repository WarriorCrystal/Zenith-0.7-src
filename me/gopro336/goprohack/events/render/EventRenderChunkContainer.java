// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.render;

import net.minecraft.client.renderer.chunk.RenderChunk;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventRenderChunkContainer extends MinecraftEvent
{
    public RenderChunk RenderChunk;
    
    public EventRenderChunkContainer(final RenderChunk renderChunk) {
        this.RenderChunk = renderChunk;
    }
}
