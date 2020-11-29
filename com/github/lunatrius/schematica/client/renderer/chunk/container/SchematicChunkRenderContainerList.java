//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.renderer.chunk.container;

import com.github.lunatrius.schematica.client.renderer.chunk.overlay.RenderOverlayList;
import com.github.lunatrius.schematica.client.renderer.chunk.overlay.RenderOverlay;
import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockRenderLayer;

public class SchematicChunkRenderContainerList extends SchematicChunkRenderContainer
{
    public void renderChunkLayer(final BlockRenderLayer layer) {
        if (this.initialized) {
            for (final RenderChunk renderChunk : this.renderChunks) {
                final ListedRenderChunk listedRenderChunk = (ListedRenderChunk)renderChunk;
                GlStateManager.pushMatrix();
                this.preRenderChunk(renderChunk);
                GL11.glCallList(listedRenderChunk.getDisplayList(layer, listedRenderChunk.getCompiledChunk()));
                GlStateManager.popMatrix();
            }
            GlStateManager.resetColor();
            this.renderChunks.clear();
        }
    }
    
    @Override
    public void renderOverlay() {
        if (this.initialized) {
            for (final RenderOverlay renderOverlay : this.renderOverlays) {
                final RenderOverlayList renderOverlayList = (RenderOverlayList)renderOverlay;
                GlStateManager.pushMatrix();
                this.preRenderChunk((RenderChunk)renderOverlay);
                GL11.glCallList(renderOverlayList.getDisplayList(BlockRenderLayer.TRANSLUCENT, renderOverlayList.getCompiledChunk()));
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.resetColor();
        this.renderOverlays.clear();
    }
}
