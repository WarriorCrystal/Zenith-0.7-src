//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.printer.registry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.block.state.IBlockState;
import java.util.Iterator;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockObserver;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockEndRod;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockLog;
import java.util.ArrayList;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockSlab;
import net.minecraft.util.math.MathHelper;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.BlockLever;
import com.github.lunatrius.schematica.block.state.BlockStateHelper;
import net.minecraft.util.EnumFacing;
import java.util.HashMap;
import java.util.LinkedHashMap;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import java.util.Map;

public class PlacementRegistry
{
    public static final PlacementRegistry INSTANCE;
    private final Map<Class<? extends Block>, PlacementData> classPlacementMap;
    private final Map<Block, PlacementData> blockPlacementMap;
    private final Map<Item, PlacementData> itemPlacementMap;
    
    public PlacementRegistry() {
        this.classPlacementMap = new LinkedHashMap<Class<? extends Block>, PlacementData>();
        this.blockPlacementMap = new HashMap<Block, PlacementData>();
        this.itemPlacementMap = new HashMap<Item, PlacementData>();
    }
    
    private void populateMappings() {
        this.classPlacementMap.clear();
        this.blockPlacementMap.clear();
        this.itemPlacementMap.clear();
        final EnumFacing facing;
        final IValidPlayerFacing playerFacingEntity = (blockState, player, pos, world) -> {
            facing = (EnumFacing)BlockStateHelper.getPropertyValue(blockState, "facing");
            return facing == player.getHorizontalFacing();
        };
        final EnumFacing facing2;
        final IValidPlayerFacing playerFacingEntityOpposite = (blockState, player, pos, world) -> {
            facing2 = (EnumFacing)BlockStateHelper.getPropertyValue(blockState, "facing");
            return facing2 == player.getHorizontalFacing().getOpposite();
        };
        final EnumFacing facing3;
        final IValidPlayerFacing playerFacingPiston = (blockState, player, pos, world) -> {
            facing3 = (EnumFacing)BlockStateHelper.getPropertyValue(blockState, "facing");
            return facing3 == EnumFacing.getDirectionFromEntityLiving(pos, player);
        };
        final EnumFacing facing4;
        final IValidPlayerFacing playerFacingObserver = (blockState, player, pos, world) -> {
            facing4 = (EnumFacing)BlockStateHelper.getPropertyValue(blockState, "facing");
            return facing4 == EnumFacing.getDirectionFromEntityLiving(pos, player).getOpposite();
        };
        final EnumFacing facing5;
        final IValidPlayerFacing playerFacingRotateY = (blockState, player, pos, world) -> {
            facing5 = (EnumFacing)BlockStateHelper.getPropertyValue(blockState, "facing");
            return facing5 == player.getHorizontalFacing().rotateY();
        };
        final BlockLever.EnumOrientation value;
        final IValidPlayerFacing playerFacingLever = (blockState, player, pos, world) -> {
            value = (BlockLever.EnumOrientation)blockState.getValue((IProperty)BlockLever.FACING);
            return !value.getFacing().getAxis().isVertical() || BlockLever.EnumOrientation.forFacings(value.getFacing(), player.getHorizontalFacing()) == value;
        };
        final int value2;
        final int facing6;
        final IValidPlayerFacing playerFacingStandingSign = (blockState, player, pos, world) -> {
            value2 = (int)blockState.getValue((IProperty)BlockStandingSign.ROTATION);
            facing6 = (MathHelper.floor((player.rotationYaw + 180.0) * 16.0 / 360.0 + 0.5) & 0xF);
            return value2 == facing6;
        };
        final IValidPlayerFacing playerFacingIgnore = (blockState, player, pos, world) -> false;
        BlockSlab.EnumBlockHalf half;
        final IOffset offsetSlab = blockState -> {
            if (!((BlockSlab)blockState.getBlock()).isDouble()) {
                half = (BlockSlab.EnumBlockHalf)blockState.getValue((IProperty)BlockSlab.HALF);
                return (half == BlockSlab.EnumBlockHalf.TOP) ? 1.0f : 0.0f;
            }
            else {
                return 0.0f;
            }
        };
        final BlockStairs.EnumHalf half2;
        final IOffset offsetStairs = blockState -> {
            half2 = (BlockStairs.EnumHalf)blockState.getValue((IProperty)BlockStairs.HALF);
            return (half2 == BlockStairs.EnumHalf.TOP) ? 1.0f : 0.0f;
        };
        final BlockTrapDoor.DoorHalf half3;
        final IOffset offsetTrapDoor = blockState -> {
            half3 = (BlockTrapDoor.DoorHalf)blockState.getValue((IProperty)BlockTrapDoor.HALF);
            return (half3 == BlockTrapDoor.DoorHalf.TOP) ? 1.0f : 0.0f;
        };
        final ArrayList<EnumFacing> list;
        final BlockLog.EnumAxis axis;
        final Iterator<EnumFacing> iterator;
        EnumFacing side;
        final IValidBlockFacing blockFacingLog = (solidSides, blockState) -> {
            list = new ArrayList<EnumFacing>();
            axis = (BlockLog.EnumAxis)blockState.getValue((IProperty)BlockLog.LOG_AXIS);
            solidSides.iterator();
            while (iterator.hasNext()) {
                side = iterator.next();
                if (axis != BlockLog.EnumAxis.fromFacingAxis(side.getAxis())) {
                    continue;
                }
                else {
                    list.add(side);
                }
            }
            return list;
        };
        final ArrayList<EnumFacing> list2;
        final EnumFacing.Axis axis2;
        final Iterator<EnumFacing> iterator2;
        EnumFacing side2;
        final IValidBlockFacing blockFacingPillar = (solidSides, blockState) -> {
            list2 = new ArrayList<EnumFacing>();
            axis2 = (EnumFacing.Axis)blockState.getValue((IProperty)BlockRotatedPillar.AXIS);
            solidSides.iterator();
            while (iterator2.hasNext()) {
                side2 = iterator2.next();
                if (axis2 != side2.getAxis()) {
                    continue;
                }
                else {
                    list2.add(side2);
                }
            }
            return list2;
        };
        final ArrayList<EnumFacing> list3;
        final IProperty propertyFacing;
        EnumFacing facing7;
        final Iterator<EnumFacing> iterator3;
        EnumFacing side3;
        final IValidBlockFacing blockFacingOpposite = (solidSides, blockState) -> {
            list3 = new ArrayList<EnumFacing>();
            propertyFacing = BlockStateHelper.getProperty(blockState, "facing");
            if (propertyFacing != null && propertyFacing.getValueClass().equals(EnumFacing.class)) {
                facing7 = (EnumFacing)blockState.getValue(propertyFacing);
                solidSides.iterator();
                while (iterator3.hasNext()) {
                    side3 = iterator3.next();
                    if (facing7.getOpposite() != side3) {
                        continue;
                    }
                    else {
                        list3.add(side3);
                    }
                }
            }
            return list3;
        };
        final ArrayList<EnumFacing> list4;
        final IProperty propertyFacing2;
        EnumFacing facing8;
        final Iterator<EnumFacing> iterator4;
        EnumFacing side4;
        final IValidBlockFacing blockFacingSame = (solidSides, blockState) -> {
            list4 = new ArrayList<EnumFacing>();
            propertyFacing2 = BlockStateHelper.getProperty(blockState, "facing");
            if (propertyFacing2 != null && propertyFacing2.getValueClass().equals(EnumFacing.class)) {
                facing8 = (EnumFacing)blockState.getValue(propertyFacing2);
                solidSides.iterator();
                while (iterator4.hasNext()) {
                    side4 = iterator4.next();
                    if (facing8 != side4) {
                        continue;
                    }
                    else {
                        list4.add(side4);
                    }
                }
            }
            return list4;
        };
        final ArrayList<EnumFacing> list5;
        final EnumFacing facing9;
        final Iterator<EnumFacing> iterator5;
        EnumFacing side5;
        final IValidBlockFacing blockFacingHopper = (solidSides, blockState) -> {
            list5 = new ArrayList<EnumFacing>();
            facing9 = (EnumFacing)blockState.getValue((IProperty)BlockHopper.FACING);
            solidSides.iterator();
            while (iterator5.hasNext()) {
                side5 = iterator5.next();
                if (facing9 != side5) {
                    continue;
                }
                else {
                    list5.add(side5);
                }
            }
            return list5;
        };
        final ArrayList<EnumFacing> list6;
        final BlockLever.EnumOrientation facing10;
        final Iterator<EnumFacing> iterator6;
        EnumFacing side6;
        final IValidBlockFacing blockFacingLever = (solidSides, blockState) -> {
            list6 = new ArrayList<EnumFacing>();
            facing10 = (BlockLever.EnumOrientation)blockState.getValue((IProperty)BlockLever.FACING);
            solidSides.iterator();
            while (iterator6.hasNext()) {
                side6 = iterator6.next();
                if (facing10.getFacing().getOpposite() != side6) {
                    continue;
                }
                else {
                    list6.add(side6);
                }
            }
            return list6;
        };
        final ArrayList<EnumFacing> list7;
        final BlockQuartz.EnumType variant;
        final Iterator<EnumFacing> iterator7;
        EnumFacing side7;
        final IValidBlockFacing blockFacingQuartz = (solidSides, blockState) -> {
            list7 = new ArrayList<EnumFacing>();
            variant = (BlockQuartz.EnumType)blockState.getValue((IProperty)BlockQuartz.VARIANT);
            solidSides.iterator();
            while (iterator7.hasNext()) {
                side7 = iterator7.next();
                if (variant == BlockQuartz.EnumType.LINES_X && side7.getAxis() != EnumFacing.Axis.X) {
                    continue;
                }
                else if (variant == BlockQuartz.EnumType.LINES_Y && side7.getAxis() != EnumFacing.Axis.Y) {
                    continue;
                }
                else if (variant == BlockQuartz.EnumType.LINES_Z && side7.getAxis() != EnumFacing.Axis.Z) {
                    continue;
                }
                else {
                    list7.add(side7);
                }
            }
            return list7;
        };
        final IExtraClick extraClickDoubleSlab = blockState -> ((BlockSlab)blockState.getBlock()).isDouble();
        this.addPlacementMapping((Class<? extends Block>)BlockLog.class, new PlacementData(blockFacingLog));
        this.addPlacementMapping((Class<? extends Block>)BlockButton.class, new PlacementData(blockFacingOpposite));
        this.addPlacementMapping((Class<? extends Block>)BlockChest.class, new PlacementData(playerFacingEntityOpposite));
        this.addPlacementMapping((Class<? extends Block>)BlockDispenser.class, new PlacementData(playerFacingPiston));
        this.addPlacementMapping((Class<? extends Block>)BlockDoor.class, new PlacementData(playerFacingEntity));
        this.addPlacementMapping((Class<? extends Block>)BlockEnderChest.class, new PlacementData(playerFacingEntityOpposite));
        this.addPlacementMapping((Class<? extends Block>)BlockEndRod.class, new PlacementData(blockFacingOpposite));
        this.addPlacementMapping((Class<? extends Block>)BlockFenceGate.class, new PlacementData(playerFacingEntity));
        this.addPlacementMapping((Class<? extends Block>)BlockFurnace.class, new PlacementData(playerFacingEntityOpposite));
        this.addPlacementMapping((Class<? extends Block>)BlockHopper.class, new PlacementData(blockFacingHopper));
        this.addPlacementMapping((Class<? extends Block>)BlockObserver.class, new PlacementData(playerFacingObserver));
        this.addPlacementMapping((Class<? extends Block>)BlockPistonBase.class, new PlacementData(playerFacingPiston));
        this.addPlacementMapping((Class<? extends Block>)BlockPumpkin.class, new PlacementData(playerFacingEntityOpposite));
        this.addPlacementMapping((Class<? extends Block>)BlockRotatedPillar.class, new PlacementData(blockFacingPillar));
        this.addPlacementMapping((Class<? extends Block>)BlockSlab.class, new PlacementData().setOffsetY(offsetSlab).setExtraClick(extraClickDoubleSlab));
        this.addPlacementMapping((Class<? extends Block>)BlockStairs.class, new PlacementData(playerFacingEntity).setOffsetY(offsetStairs));
        this.addPlacementMapping((Class<? extends Block>)BlockTorch.class, new PlacementData(blockFacingOpposite));
        this.addPlacementMapping((Class<? extends Block>)BlockTrapDoor.class, new PlacementData(blockFacingOpposite).setOffsetY(offsetTrapDoor));
        this.addPlacementMapping(Blocks.ANVIL, new PlacementData(playerFacingRotateY));
        this.addPlacementMapping(Blocks.CHAIN_COMMAND_BLOCK, new PlacementData(playerFacingEntityOpposite));
        this.addPlacementMapping(Blocks.COCOA, new PlacementData(blockFacingSame));
        this.addPlacementMapping(Blocks.END_PORTAL_FRAME, new PlacementData(playerFacingEntityOpposite));
        this.addPlacementMapping(Blocks.LADDER, new PlacementData(blockFacingOpposite));
        this.addPlacementMapping(Blocks.LEVER, new PlacementData(playerFacingLever, blockFacingLever));
        this.addPlacementMapping(Blocks.QUARTZ_BLOCK, new PlacementData(blockFacingQuartz));
        this.addPlacementMapping(Blocks.REPEATING_COMMAND_BLOCK, new PlacementData(playerFacingEntityOpposite));
        this.addPlacementMapping(Blocks.STANDING_SIGN, new PlacementData(playerFacingStandingSign));
        this.addPlacementMapping((Block)Blocks.TRIPWIRE_HOOK, new PlacementData(blockFacingOpposite));
        this.addPlacementMapping(Blocks.WALL_SIGN, new PlacementData(blockFacingOpposite));
        this.addPlacementMapping(Items.COMPARATOR, new PlacementData(playerFacingEntityOpposite));
        this.addPlacementMapping(Items.REPEATER, new PlacementData(playerFacingEntityOpposite));
        this.addPlacementMapping(Blocks.BED, new PlacementData(playerFacingIgnore));
        this.addPlacementMapping(Blocks.END_PORTAL, new PlacementData(playerFacingIgnore));
        this.addPlacementMapping((Block)Blocks.PISTON_EXTENSION, new PlacementData(playerFacingIgnore));
        this.addPlacementMapping((Block)Blocks.PISTON_HEAD, new PlacementData(playerFacingIgnore));
        this.addPlacementMapping((Block)Blocks.PORTAL, new PlacementData(playerFacingIgnore));
        this.addPlacementMapping((Block)Blocks.SKULL, new PlacementData(playerFacingIgnore));
        this.addPlacementMapping(Blocks.STANDING_BANNER, new PlacementData(playerFacingIgnore));
        this.addPlacementMapping(Blocks.WALL_BANNER, new PlacementData(playerFacingIgnore));
    }
    
    private PlacementData addPlacementMapping(final Class<? extends Block> clazz, final PlacementData data) {
        if (clazz == null || data == null) {
            return null;
        }
        return this.classPlacementMap.put(clazz, data);
    }
    
    private PlacementData addPlacementMapping(final Block block, final PlacementData data) {
        if (block == null || data == null) {
            return null;
        }
        return this.blockPlacementMap.put(block, data);
    }
    
    private PlacementData addPlacementMapping(final Item item, final PlacementData data) {
        if (item == null || data == null) {
            return null;
        }
        return this.itemPlacementMap.put(item, data);
    }
    
    public PlacementData getPlacementData(final IBlockState blockState, final ItemStack itemStack) {
        final Item item = itemStack.getItem();
        final PlacementData placementDataItem = this.itemPlacementMap.get(item);
        if (placementDataItem != null) {
            return placementDataItem;
        }
        final Block block = blockState.getBlock();
        final PlacementData placementDataBlock = this.blockPlacementMap.get(block);
        if (placementDataBlock != null) {
            return placementDataBlock;
        }
        for (final Class<? extends Block> clazz : this.classPlacementMap.keySet()) {
            if (clazz.isInstance(block)) {
                return this.classPlacementMap.get(clazz);
            }
        }
        return null;
    }
    
    static {
        (INSTANCE = new PlacementRegistry()).populateMappings();
    }
}
