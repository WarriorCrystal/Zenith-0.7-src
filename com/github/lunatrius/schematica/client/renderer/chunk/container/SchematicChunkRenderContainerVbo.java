//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.renderer.chunk.container;

import com.github.lunatrius.schematica.client.renderer.chunk.overlay.RenderOverlay;
import java.util.List;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import java.util.Iterator;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockRenderLayer;

public class SchematicChunkRenderContainerVbo extends SchematicChunkRenderContainer
{
    public void renderChunkLayer(final BlockRenderLayer layer) {
        this.preRenderChunk();
        if (this.initialized) {
            for (final RenderChunk renderChunk : this.renderChunks) {
                final VertexBuffer vertexbuffer = renderChunk.getVertexBufferByLayer(layer.ordinal());
                GlStateManager.pushMatrix();
                this.preRenderChunk(renderChunk);
                renderChunk.multModelviewMatrix();
                vertexbuffer.bindBuffer();
                this.setupArrayPointers();
                vertexbuffer.drawArrays(7);
                GlStateManager.popMatrix();
            }
            OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
            GlStateManager.resetColor();
            this.renderChunks.clear();
        }
        this.postRenderChunk();
    }
    
    private void preRenderChunk() {
        GL11.glEnableClientState(32884);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnableClientState(32888);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnableClientState(32888);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnableClientState(32886);
    }
    
    private void postRenderChunk() {
        final List<VertexFormatElement> elements = (List<VertexFormatElement>)DefaultVertexFormats.BLOCK.getElements();
        for (final VertexFormatElement element : elements) {
            final VertexFormatElement.EnumUsage usage = element.getUsage();
            final int index = element.getIndex();
            switch (usage) {
                case POSITION: {
                    GL11.glDisableClientState(32884);
                    continue;
                }
                case UV: {
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + index);
                    GL11.glDisableClientState(32888);
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                    continue;
                }
                case COLOR: {
                    GL11.glDisableClientState(32886);
                    GlStateManager.resetColor();
                    continue;
                }
            }
        }
    }
    
    private void setupArrayPointers() {
        GL11.glVertexPointer(3, 5126, 28, 0L);
        GL11.glColorPointer(4, 5121, 28, 12L);
        GL11.glTexCoordPointer(2, 5126, 28, 16L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexCoordPointer(2, 5122, 28, 24L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    @Override
    public void renderOverlay() {
        if (this.initialized) {
            this.preRenderOverlay();
            for (final RenderOverlay renderOverlay : this.renderOverlays) {
                final VertexBuffer vertexBuffer = renderOverlay.getVertexBufferByLayer(BlockRenderLayer.TRANSLUCENT.ordinal());
                GlStateManager.pushMatrix();
                this.preRenderChunk((RenderChunk)renderOverlay);
                renderOverlay.multModelviewMatrix();
                vertexBuffer.bindBuffer();
                this.setupArrayPointersOverlay();
                vertexBuffer.drawArrays(7);
                GlStateManager.popMatrix();
            }
            OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
            GlStateManager.resetColor();
            this.renderOverlays.clear();
            this.postRenderOverlay();
        }
    }
    
    private void preRenderOverlay() {
        GL11.glEnableClientState(32884);
        GL11.glEnableClientState(32886);
    }
    
    private void postRenderOverlay() {
        GL11.glDisableClientState(32886);
        GL11.glDisableClientState(32884);
    }
    
    private void setupArrayPointersOverlay() {
        GL11.glVertexPointer(3, 5126, 16, 0L);
        GL11.glColorPointer(4, 5121, 16, 12L);
    }
}
