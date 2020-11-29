//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.util;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyDirection;
import com.github.lunatrius.schematica.block.state.BlockStateHelper;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import com.github.lunatrius.core.util.math.BlockPosHelper;
import com.github.lunatrius.core.util.math.MBlockPos;
import net.minecraft.util.math.Vec3i;
import java.util.Iterator;
import com.github.lunatrius.schematica.world.storage.Schematic;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.tileentity.TileEntity;
import com.github.lunatrius.schematica.api.ISchematic;
import net.minecraft.util.EnumFacing;
import com.github.lunatrius.schematica.client.world.SchematicWorld;

public class FlipHelper
{
    public static final FlipHelper INSTANCE;
    
    public boolean flip(final SchematicWorld world, final EnumFacing axis, final boolean forced) {
        if (world == null) {
            return false;
        }
        try {
            final ISchematic schematic = world.getSchematic();
            final Schematic schematicFlipped = this.flip(schematic, axis, forced);
            world.setSchematic(schematicFlipped);
            for (final TileEntity tileEntity : world.getTileEntities()) {
                world.initializeTileEntity(tileEntity);
            }
            return true;
        }
        catch (FlipException fe) {
            Reference.logger.error(fe.getMessage());
        }
        catch (Exception e) {
            Reference.logger.fatal("Something went wrong!", (Throwable)e);
        }
        return false;
    }
    
    public Schematic flip(final ISchematic schematic, final EnumFacing axis, final boolean forced) throws FlipException {
        final Vec3i dimensionsFlipped = new Vec3i(schematic.getWidth(), schematic.getHeight(), schematic.getLength());
        final Schematic schematicFlipped = new Schematic(schematic.getIcon(), dimensionsFlipped.getX(), dimensionsFlipped.getY(), dimensionsFlipped.getZ(), schematic.getAuthor());
        final MBlockPos tmp = new MBlockPos();
        for (final MBlockPos pos : BlockPosHelper.getAllInBox(0, 0, 0, schematic.getWidth() - 1, schematic.getHeight() - 1, schematic.getLength() - 1)) {
            final IBlockState blockState = schematic.getBlockState(pos);
            final IBlockState blockStateFlipped = this.flipBlock(blockState, axis, forced);
            schematicFlipped.setBlockState(this.flipPos(pos, axis, dimensionsFlipped, tmp), blockStateFlipped);
        }
        final List<TileEntity> tileEntities = schematic.getTileEntities();
        for (final TileEntity tileEntity : tileEntities) {
            final BlockPos pos2 = tileEntity.getPos();
            tileEntity.setPos(new BlockPos((Vec3i)this.flipPos(pos2, axis, dimensionsFlipped, tmp)));
            schematicFlipped.setTileEntity(tileEntity.getPos(), tileEntity);
        }
        return schematicFlipped;
    }
    
    private BlockPos flipPos(final BlockPos pos, final EnumFacing axis, final Vec3i dimensions, final MBlockPos flipped) throws FlipException {
        switch (axis) {
            case DOWN:
            case UP: {
                return flipped.set(pos.getX(), dimensions.getY() - 1 - pos.getY(), pos.getZ());
            }
            case NORTH:
            case SOUTH: {
                return flipped.set(pos.getX(), pos.getY(), dimensions.getZ() - 1 - pos.getZ());
            }
            case WEST:
            case EAST: {
                return flipped.set(dimensions.getX() - 1 - pos.getX(), pos.getY(), pos.getZ());
            }
            default: {
                throw new FlipException("'%s' is not a valid axis!", new Object[] { axis.getName() });
            }
        }
    }
    
    private IBlockState flipBlock(final IBlockState blockState, final EnumFacing axis, final boolean forced) throws FlipException {
        final IProperty propertyFacing = BlockStateHelper.getProperty(blockState, "facing");
        if (propertyFacing instanceof PropertyDirection) {
            final Comparable value = blockState.getValue(propertyFacing);
            if (value instanceof EnumFacing) {
                final EnumFacing facing = getFlippedFacing(axis, (EnumFacing)value);
                if (propertyFacing.getAllowedValues().contains(facing)) {
                    return blockState.withProperty(propertyFacing, (Comparable)facing);
                }
            }
        }
        else if (propertyFacing instanceof PropertyEnum) {
            if (BlockLever.EnumOrientation.class.isAssignableFrom(propertyFacing.getValueClass())) {
                final BlockLever.EnumOrientation orientation = (BlockLever.EnumOrientation)blockState.getValue(propertyFacing);
                final BlockLever.EnumOrientation orientationRotated = getFlippedLeverFacing(axis, orientation);
                if (propertyFacing.getAllowedValues().contains(orientationRotated)) {
                    return blockState.withProperty(propertyFacing, (Comparable)orientationRotated);
                }
            }
        }
        else if (propertyFacing != null) {
            Reference.logger.error("'{}': found 'facing' property with unknown type {}", Block.REGISTRY.getNameForObject((Object)blockState.getBlock()), (Object)propertyFacing.getClass().getSimpleName());
        }
        if (!forced && propertyFacing != null) {
            throw new FlipException("'%s' cannot be flipped across '%s'", new Object[] { Block.REGISTRY.getNameForObject((Object)blockState.getBlock()), axis });
        }
        return blockState;
    }
    
    private static EnumFacing getFlippedFacing(final EnumFacing axis, final EnumFacing side) {
        if (axis.getAxis() == side.getAxis()) {
            return side.getOpposite();
        }
        return side;
    }
    
    private static BlockLever.EnumOrientation getFlippedLeverFacing(final EnumFacing source, final BlockLever.EnumOrientation side) {
        if (source.getAxis() != side.getFacing().getAxis()) {
            return side;
        }
        EnumFacing facing;
        if (side == BlockLever.EnumOrientation.UP_Z || side == BlockLever.EnumOrientation.DOWN_Z) {
            facing = EnumFacing.NORTH;
        }
        else if (side == BlockLever.EnumOrientation.UP_X || side == BlockLever.EnumOrientation.DOWN_X) {
            facing = EnumFacing.WEST;
        }
        else {
            facing = side.getFacing();
        }
        final EnumFacing facingFlipped = getFlippedFacing(source, side.getFacing());
        return BlockLever.EnumOrientation.forFacings(facingFlipped, facing);
    }
    
    static {
        INSTANCE = new FlipHelper();
    }
    
    public static class FlipException extends Exception
    {
        public FlipException(final String message, final Object... args) {
            super(String.format(message, args));
        }
    }
}
