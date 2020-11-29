//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.renderer.chunk.overlay;

import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.world.World;

public class RenderOverlayList extends RenderOverlay
{
    private final int displayList;
    
    public RenderOverlayList(final World world, final RenderGlobal renderGlobal, final BlockPos pos, final int index) {
        super(world, renderGlobal, index);
        this.displayList = GLAllocation.generateDisplayLists(1);
    }
    
    public int getDisplayList(final BlockRenderLayer layer, final CompiledChunk compiledChunk) {
        return compiledChunk.isLayerEmpty(layer) ? -1 : this.displayList;
    }
    
    @Override
    public void deleteGlResources() {
        super.deleteGlResources();
        GLAllocation.deleteDisplayLists(this.displayList);
    }
}
