//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.renderer.chunk;

import net.minecraft.util.BlockRenderLayer;
import net.minecraft.client.renderer.chunk.CompiledChunk;

public class CompiledOverlay extends CompiledChunk
{
    public void setLayerStarted(final BlockRenderLayer layer) {
        if (layer == BlockRenderLayer.TRANSLUCENT) {
            super.setLayerStarted(layer);
        }
    }
    
    public void setLayerUsed(final BlockRenderLayer layer) {
        if (layer == BlockRenderLayer.TRANSLUCENT) {
            super.setLayerUsed(layer);
        }
    }
    
    public boolean isLayerStarted(final BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT && super.isLayerStarted(layer);
    }
    
    public boolean isLayerEmpty(final BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT && super.isLayerEmpty(layer);
    }
}
