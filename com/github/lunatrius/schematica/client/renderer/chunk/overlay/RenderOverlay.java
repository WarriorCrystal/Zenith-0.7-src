//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.renderer.chunk.overlay;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import java.util.Iterator;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.BlockPos;
import com.github.lunatrius.core.client.renderer.GeometryTessellator;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.world.ChunkCache;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.schematica.client.renderer.chunk.CompiledOverlay;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.world.World;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.chunk.RenderChunk;

public class RenderOverlay extends RenderChunk
{
    private final VertexBuffer vertexBuffer;
    
    public RenderOverlay(final World world, final RenderGlobal renderGlobal, final int index) {
        super(world, renderGlobal, index);
        this.vertexBuffer = (OpenGlHelper.useVbo() ? new VertexBuffer(DefaultVertexFormats.POSITION_COLOR) : null);
    }
    
    public VertexBuffer getVertexBufferByLayer(final int layer) {
        return this.vertexBuffer;
    }
    
    public void rebuildChunk(final float x, final float y, final float z, final ChunkCompileTaskGenerator generator) {
        final CompiledOverlay compiledOverlay = new CompiledOverlay();
        final BlockPos from = this.getPosition();
        final BlockPos to = from.add(15, 15, 15);
        final BlockPos fromEx = from.add(-1, -1, -1);
        final BlockPos toEx = to.add(1, 1, 1);
        generator.getLock().lock();
        final SchematicWorld schematic = (SchematicWorld)this.world;
        ChunkCache chunkCache;
        try {
            if (generator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
                return;
            }
            if (from.getX() < 0 || from.getZ() < 0 || from.getX() >= schematic.getWidth() || from.getZ() >= schematic.getLength()) {
                generator.setCompiledChunk(CompiledChunk.DUMMY);
                return;
            }
            chunkCache = new ChunkCache(this.world, fromEx, toEx, 1);
            generator.setCompiledChunk((CompiledChunk)compiledOverlay);
        }
        finally {
            generator.getLock().unlock();
        }
        final VisGraph visgraph = new VisGraph();
        if (!chunkCache.isEmpty()) {
            ++RenderOverlay.renderChunksUpdated;
            final World mcWorld = (World)Minecraft.getMinecraft().world;
            final BlockRenderLayer layer = BlockRenderLayer.TRANSLUCENT;
            final BufferBuilder buffer = generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(layer);
            GeometryTessellator.setStaticDelta(ConfigurationHandler.blockDelta);
            final BlockType[][][] types = new BlockType[18][18][18];
            final BlockPos.MutableBlockPos mcPos = new BlockPos.MutableBlockPos();
            for (final BlockPos.MutableBlockPos pos : BlockPos.getAllInBoxMutable(fromEx, toEx)) {
                if (schematic.isInside((BlockPos)pos)) {
                    if (!schematic.layerMode.shouldUseLayer(schematic, pos.getY())) {
                        continue;
                    }
                    final int secX = pos.getX() - fromEx.getX();
                    final int secY = pos.getY() - fromEx.getY();
                    final int secZ = pos.getZ() - fromEx.getZ();
                    final IBlockState schBlockState = schematic.getBlockState((BlockPos)pos);
                    final Block schBlock = schBlockState.getBlock();
                    if (schBlockState.isOpaqueCube()) {
                        visgraph.setOpaqueCube((BlockPos)pos);
                    }
                    mcPos.setPos(pos.getX() + schematic.position.getX(), pos.getY() + schematic.position.getY(), pos.getZ() + schematic.position.getZ());
                    final IBlockState mcBlockState = mcWorld.getBlockState((BlockPos)mcPos);
                    final Block mcBlock = mcBlockState.getBlock();
                    final boolean isSchAirBlock = schematic.isAirBlock((BlockPos)pos);
                    final boolean isMcAirBlock = mcWorld.isAirBlock((BlockPos)mcPos) || ConfigurationHandler.isExtraAirBlock(mcBlock);
                    if (ConfigurationHandler.highlightAir && !isMcAirBlock && isSchAirBlock) {
                        types[secX][secY][secZ] = BlockType.EXTRA_BLOCK;
                    }
                    else {
                        if (!ConfigurationHandler.highlight) {
                            continue;
                        }
                        if (!isMcAirBlock) {
                            if (schBlock != mcBlock) {
                                types[secX][secY][secZ] = BlockType.WRONG_BLOCK;
                            }
                            else {
                                if (schBlock.getMetaFromState(schBlockState) == mcBlock.getMetaFromState(mcBlockState)) {
                                    continue;
                                }
                                types[secX][secY][secZ] = BlockType.WRONG_META;
                            }
                        }
                        else {
                            if (isSchAirBlock) {
                                continue;
                            }
                            types[secX][secY][secZ] = BlockType.MISSING_BLOCK;
                        }
                    }
                }
            }
            for (final BlockPos.MutableBlockPos pos : BlockPos.getAllInBoxMutable(from, to)) {
                final int secX = pos.getX() - fromEx.getX();
                final int secY = pos.getY() - fromEx.getY();
                final int secZ = pos.getZ() - fromEx.getZ();
                final BlockType type = types[secX][secY][secZ];
                if (type != null) {
                    if (!compiledOverlay.isLayerStarted(layer)) {
                        compiledOverlay.setLayerStarted(layer);
                        this.preRenderBlocks(buffer, from);
                    }
                    final int sides = this.getSides(types, secX, secY, secZ);
                    GeometryTessellator.drawCuboid(buffer, (BlockPos)pos, sides, 0x3F000000 | type.color);
                    compiledOverlay.setLayerUsed(layer);
                }
            }
            if (compiledOverlay.isLayerStarted(layer)) {
                this.postRenderBlocks(layer, x, y, z, buffer, (CompiledChunk)compiledOverlay);
            }
        }
        compiledOverlay.setVisibility(visgraph.computeVisibility());
    }
    
    private int getSides(final BlockType[][][] types, final int x, final int y, final int z) {
        if (x <= 0 || x >= 17) {
            throw new IndexOutOfBoundsException("x cannot be in padding: " + x);
        }
        if (y <= 0 || y >= 17) {
            throw new IndexOutOfBoundsException("y cannot be in padding: " + y);
        }
        if (z <= 0 || z >= 17) {
            throw new IndexOutOfBoundsException("z cannot be in padding: " + z);
        }
        int sides = 0;
        final BlockType type = types[x][y][z];
        if (types[x][y - 1][z] != type) {
            sides |= 0x1;
        }
        if (types[x][y + 1][z] != type) {
            sides |= 0x2;
        }
        if (types[x][y][z - 1] != type) {
            sides |= 0x4;
        }
        if (types[x][y][z + 1] != type) {
            sides |= 0x8;
        }
        if (types[x - 1][y][z] != type) {
            sides |= 0x10;
        }
        if (types[x + 1][y][z] != type) {
            sides |= 0x20;
        }
        return sides;
    }
    
    public void preRenderBlocks(final BufferBuilder buffer, final BlockPos pos) {
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.setTranslation((double)(-pos.getX()), (double)(-pos.getY()), (double)(-pos.getZ()));
    }
    
    public void deleteGlResources() {
        super.deleteGlResources();
        if (this.vertexBuffer != null) {
            this.vertexBuffer.deleteGlBuffers();
        }
    }
    
    private enum BlockType
    {
        EXTRA_BLOCK(12517567), 
        WRONG_BLOCK(16711680), 
        WRONG_META(12541696), 
        MISSING_BLOCK(49151);
        
        public final int color;
        
        private BlockType(final int color) {
            this.color = color;
        }
    }
}
