//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.renderer;

import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import net.minecraft.util.math.Vec3i;
import net.minecraft.init.Blocks;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;
import net.minecraft.world.ChunkCache;

public class SchematicRenderCache extends ChunkCache
{
    private final Minecraft minecraft;
    
    public SchematicRenderCache(final World world, final BlockPos from, final BlockPos to, final int subtract) {
        super(world, from, to, subtract);
        this.minecraft = Minecraft.getMinecraft();
    }
    
    public IBlockState getBlockState(final BlockPos pos) {
        final BlockPos schPos = ClientProxy.schematic.position;
        if (schPos == null) {
            return Blocks.AIR.getDefaultState();
        }
        final BlockPos realPos = pos.add((Vec3i)schPos);
        final World world = (World)this.minecraft.world;
        if (world == null || (!world.isAirBlock(realPos) && !ConfigurationHandler.isExtraAirBlock(world.getBlockState(realPos).getBlock()))) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getBlockState(pos);
    }
}
