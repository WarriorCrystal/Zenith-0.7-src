//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.renderer.chunk;

import com.google.common.util.concurrent.Futures;
import com.github.lunatrius.schematica.client.renderer.chunk.overlay.RenderOverlayList;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.Minecraft;
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.BlockRenderLayer;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class OverlayRenderDispatcher extends ChunkRenderDispatcher
{
    public OverlayRenderDispatcher() {
    }
    
    public OverlayRenderDispatcher(final int countRenderBuilders) {
        super(countRenderBuilders);
    }
    
    public ListenableFuture<Object> uploadChunk(final BlockRenderLayer layer, final BufferBuilder buffer, final RenderChunk renderChunk, final CompiledChunk compiledChunk, final double distanceSq) {
        if (!Minecraft.getMinecraft().isCallingFromMinecraftThread() || OpenGlHelper.useVbo()) {
            return (ListenableFuture<Object>)super.uploadChunk(layer, buffer, renderChunk, compiledChunk, distanceSq);
        }
        this.uploadDisplayList(buffer, ((RenderOverlayList)renderChunk).getDisplayList(layer, compiledChunk), renderChunk);
        buffer.setTranslation(0.0, 0.0, 0.0);
        return (ListenableFuture<Object>)Futures.immediateFuture((Object)null);
    }
}
