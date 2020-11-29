//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.renderer.chunk.container;

import com.google.common.collect.Lists;
import com.github.lunatrius.schematica.client.renderer.chunk.overlay.RenderOverlay;
import java.util.List;
import net.minecraft.client.renderer.ChunkRenderContainer;

public abstract class SchematicChunkRenderContainer extends ChunkRenderContainer
{
    protected List<RenderOverlay> renderOverlays;
    
    public SchematicChunkRenderContainer() {
        this.renderOverlays = (List<RenderOverlay>)Lists.newArrayListWithCapacity(17424);
    }
    
    public void initialize(final double viewEntityX, final double viewEntityY, final double viewEntityZ) {
        super.initialize(viewEntityX, viewEntityY, viewEntityZ);
        this.renderOverlays.clear();
    }
    
    public void addRenderOverlay(final RenderOverlay renderOverlay) {
        this.renderOverlays.add(renderOverlay);
    }
    
    public abstract void renderOverlay();
}
