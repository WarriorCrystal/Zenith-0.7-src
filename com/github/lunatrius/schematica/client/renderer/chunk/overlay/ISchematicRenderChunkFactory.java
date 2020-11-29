// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.renderer.chunk.overlay;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.world.World;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;

public interface ISchematicRenderChunkFactory extends IRenderChunkFactory
{
    RenderOverlay makeRenderOverlay(final World p0, final RenderGlobal p1, final int p2);
}
