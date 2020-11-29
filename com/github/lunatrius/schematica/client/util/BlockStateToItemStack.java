//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.util;

import net.minecraft.block.Block;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.block.state.IBlockState;
import mcp.MethodsReturnNonnullByDefault;

@MethodsReturnNonnullByDefault
public class BlockStateToItemStack
{
    public static ItemStack getItemStack(final IBlockState blockState, final RayTraceResult rayTraceResult, final SchematicWorld world, final BlockPos pos, final EntityPlayer player) {
        final Block block = blockState.getBlock();
        try {
            final ItemStack itemStack = block.getPickBlock(blockState, rayTraceResult, (World)world, pos, player);
            if (!itemStack.isEmpty()) {
                return itemStack;
            }
        }
        catch (Exception e) {
            Reference.logger.debug("Could not get the pick block for: {}", (Object)blockState, (Object)e);
        }
        return ItemStack.EMPTY;
    }
}
