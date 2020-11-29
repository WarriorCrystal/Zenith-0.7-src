//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.util;

import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import java.util.Iterator;
import net.minecraft.inventory.IInventory;
import com.github.lunatrius.core.entity.EntityHelper;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraft.block.BlockLiquid;
import net.minecraftforge.fluids.IFluidBlock;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.item.ItemStack;
import com.github.lunatrius.schematica.block.state.BlockStateHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.init.Blocks;
import com.github.lunatrius.core.util.math.BlockPosHelper;
import net.minecraft.util.math.BlockPos;
import com.github.lunatrius.core.util.math.MBlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.World;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import net.minecraft.entity.player.EntityPlayer;

public class BlockList
{
    public List<WrappedItemStack> getList(final EntityPlayer player, final SchematicWorld world, final World mcWorld) {
        final List<WrappedItemStack> blockList = new ArrayList<WrappedItemStack>();
        if (world == null) {
            return blockList;
        }
        final RayTraceResult rtr = new RayTraceResult((Entity)player);
        final MBlockPos mcPos = new MBlockPos();
        for (final MBlockPos pos : BlockPosHelper.getAllInBox(BlockPos.ORIGIN, new BlockPos(world.getWidth() - 1, world.getHeight() - 1, world.getLength() - 1))) {
            if (!world.layerMode.shouldUseLayer(world, pos.getY())) {
                continue;
            }
            final IBlockState blockState = world.getBlockState(pos);
            final Block block = blockState.getBlock();
            if (block == Blocks.AIR) {
                continue;
            }
            if (world.isAirBlock(pos)) {
                continue;
            }
            mcPos.set((Vec3i)world.position.add((Vec3i)pos));
            final IBlockState mcBlockState = mcWorld.getBlockState((BlockPos)mcPos);
            final boolean isPlaced = BlockStateHelper.areBlockStatesEqual(blockState, mcBlockState);
            ItemStack stack = ItemStack.EMPTY;
            try {
                stack = block.getPickBlock(blockState, rtr, (World)world, (BlockPos)pos, player);
            }
            catch (Exception e) {
                Reference.logger.warn("Could not get the pick block for: {}", (Object)blockState, (Object)e);
            }
            if (block instanceof IFluidBlock || block instanceof BlockLiquid) {
                final IFluidHandler fluidHandler = FluidUtil.getFluidHandler((World)world, (BlockPos)pos, (EnumFacing)null);
                final FluidActionResult fluidActionResult = FluidUtil.tryFillContainer(new ItemStack(Items.BUCKET), fluidHandler, 1000, (EntityPlayer)null, false);
                if (fluidActionResult.isSuccess()) {
                    final ItemStack result = fluidActionResult.getResult();
                    if (!result.isEmpty()) {
                        stack = result;
                    }
                }
            }
            if (stack == null) {
                Reference.logger.error("Could not find the item for: {} (getPickBlock() returned null, this is a bug)", (Object)blockState);
            }
            else if (stack.isEmpty()) {
                Reference.logger.warn("Could not find the item for: {}", (Object)blockState);
            }
            else {
                int count = 1;
                if (block instanceof BlockSlab && ((BlockSlab)block).isDouble()) {
                    count = 2;
                }
                final WrappedItemStack wrappedItemStack = this.findOrCreateWrappedItemStackFor(blockList, stack);
                if (isPlaced) {
                    final WrappedItemStack wrappedItemStack3 = wrappedItemStack;
                    wrappedItemStack3.placed += count;
                }
                final WrappedItemStack wrappedItemStack4 = wrappedItemStack;
                wrappedItemStack4.total += count;
            }
        }
        for (final WrappedItemStack wrappedItemStack2 : blockList) {
            if (player.capabilities.isCreativeMode) {
                wrappedItemStack2.inventory = -1;
            }
            else {
                wrappedItemStack2.inventory = EntityHelper.getItemCountInInventory((IInventory)player.inventory, wrappedItemStack2.itemStack.getItem(), wrappedItemStack2.itemStack.getItemDamage());
            }
        }
        return blockList;
    }
    
    private WrappedItemStack findOrCreateWrappedItemStackFor(final List<WrappedItemStack> blockList, final ItemStack itemStack) {
        for (final WrappedItemStack wrappedItemStack : blockList) {
            if (wrappedItemStack.itemStack.isItemEqual(itemStack)) {
                return wrappedItemStack;
            }
        }
        final WrappedItemStack wrappedItemStack2 = new WrappedItemStack(itemStack.copy());
        blockList.add(wrappedItemStack2);
        return wrappedItemStack2;
    }
    
    public static class WrappedItemStack
    {
        public ItemStack itemStack;
        public int placed;
        public int total;
        public int inventory;
        
        public WrappedItemStack(final ItemStack itemStack) {
            this(itemStack, 0, 0);
        }
        
        public WrappedItemStack(final ItemStack itemStack, final int placed, final int total) {
            this.itemStack = itemStack;
            this.placed = placed;
            this.total = total;
        }
        
        public String getItemStackDisplayName() {
            return this.itemStack.getItem().getItemStackDisplayName(this.itemStack);
        }
        
        public String getFormattedAmount() {
            final char color = (this.placed < this.total) ? 'c' : 'a';
            return String.format("§%c%s§r/%s", color, getFormattedStackAmount(this.itemStack, this.placed), getFormattedStackAmount(this.itemStack, this.total));
        }
        
        public String getFormattedAmountMissing(final String strAvailable, final String strMissing) {
            final int need = this.total - (this.inventory + this.placed);
            if (this.inventory != -1 && need > 0) {
                return String.format("§c%s: %s", strMissing, getFormattedStackAmount(this.itemStack, need));
            }
            return String.format("§a%s", strAvailable);
        }
        
        private static String getFormattedStackAmount(final ItemStack itemStack, final int amount) {
            final int stackSize = itemStack.getMaxStackSize();
            return String.format("%d", amount);
        }
    }
}
