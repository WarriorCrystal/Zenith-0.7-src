//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.world.chunk;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ChunkSchematic extends Chunk
{
    private final World world;
    
    public ChunkSchematic(final World world, final int x, final int z) {
        super(world, x, z);
        this.world = world;
    }
    
    protected void generateHeightMap() {
    }
    
    public void generateSkylightMap() {
    }
    
    public IBlockState getBlockState(final BlockPos pos) {
        return this.world.getBlockState(pos);
    }
    
    public boolean isEmptyBetween(final int startY, final int endY) {
        return false;
    }
    
    public TileEntity getTileEntity(final BlockPos pos, final Chunk.EnumCreateEntityType createEntityType) {
        return this.world.getTileEntity(pos);
    }
}
