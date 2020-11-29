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
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.BlockPos;
import com.github.lunatrius.core.util.math.BlockPosHelper;
import com.github.lunatrius.core.util.math.MBlockPos;
import java.util.Iterator;
import com.github.lunatrius.schematica.world.storage.Schematic;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.tileentity.TileEntity;
import com.github.lunatrius.schematica.api.ISchematic;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockLog;
import net.minecraft.util.EnumFacing;

public class RotationHelper
{
    public static final RotationHelper INSTANCE;
    private static final EnumFacing[][] FACINGS;
    private static final EnumFacing.Axis[][] AXISES;
    private static final BlockLog.EnumAxis[][] AXISES_LOG;
    private static final BlockQuartz.EnumType[][] AXISES_QUARTZ;
    
    public boolean rotate(final SchematicWorld world, final EnumFacing axis, final boolean forced) {
        if (world == null) {
            return false;
        }
        try {
            final ISchematic schematic = world.getSchematic();
            final Schematic schematicRotated = this.rotate(schematic, axis, forced);
            this.updatePosition(world, axis);
            world.setSchematic(schematicRotated);
            for (final TileEntity tileEntity : world.getTileEntities()) {
                world.initializeTileEntity(tileEntity);
            }
            return true;
        }
        catch (RotationException re) {
            Reference.logger.error(re.getMessage());
        }
        catch (Exception e) {
            Reference.logger.fatal("Something went wrong!", (Throwable)e);
        }
        return false;
    }
    
    private void updatePosition(final SchematicWorld world, final EnumFacing axis) {
        switch (axis) {
            case DOWN:
            case UP: {
                final int offset = (world.getWidth() - world.getLength()) / 2;
                final MBlockPos position = world.position;
                position.x += offset;
                final MBlockPos position2 = world.position;
                position2.z -= offset;
                break;
            }
            case NORTH:
            case SOUTH: {
                final int offset = (world.getWidth() - world.getHeight()) / 2;
                final MBlockPos position3 = world.position;
                position3.x += offset;
                final MBlockPos position4 = world.position;
                position4.y -= offset;
                break;
            }
            case WEST:
            case EAST: {
                final int offset = (world.getHeight() - world.getLength()) / 2;
                final MBlockPos position5 = world.position;
                position5.y += offset;
                final MBlockPos position6 = world.position;
                position6.z -= offset;
                break;
            }
        }
    }
    
    public Schematic rotate(final ISchematic schematic, final EnumFacing axis, final boolean forced) throws RotationException {
        final Vec3i dimensionsRotated = this.rotateDimensions(axis, schematic.getWidth(), schematic.getHeight(), schematic.getLength());
        final Schematic schematicRotated = new Schematic(schematic.getIcon(), dimensionsRotated.getX(), dimensionsRotated.getY(), dimensionsRotated.getZ(), schematic.getAuthor());
        final MBlockPos tmp = new MBlockPos();
        for (final MBlockPos pos : BlockPosHelper.getAllInBox(0, 0, 0, schematic.getWidth() - 1, schematic.getHeight() - 1, schematic.getLength() - 1)) {
            final IBlockState blockState = schematic.getBlockState(pos);
            final IBlockState blockStateRotated = this.rotateBlock(blockState, axis, forced);
            schematicRotated.setBlockState(this.rotatePos(pos, axis, dimensionsRotated, tmp), blockStateRotated);
        }
        final List<TileEntity> tileEntities = schematic.getTileEntities();
        for (final TileEntity tileEntity : tileEntities) {
            final BlockPos pos2 = tileEntity.getPos();
            tileEntity.setPos(new BlockPos((Vec3i)this.rotatePos(pos2, axis, dimensionsRotated, tmp)));
            schematicRotated.setTileEntity(tileEntity.getPos(), tileEntity);
        }
        return schematicRotated;
    }
    
    private Vec3i rotateDimensions(final EnumFacing axis, final int width, final int height, final int length) throws RotationException {
        switch (axis) {
            case DOWN:
            case UP: {
                return new Vec3i(length, height, width);
            }
            case NORTH:
            case SOUTH: {
                return new Vec3i(height, width, length);
            }
            case WEST:
            case EAST: {
                return new Vec3i(width, length, height);
            }
            default: {
                throw new RotationException("'%s' is not a valid axis!", new Object[] { axis.getName() });
            }
        }
    }
    
    private BlockPos rotatePos(final BlockPos pos, final EnumFacing axis, final Vec3i dimensions, final MBlockPos rotated) throws RotationException {
        switch (axis) {
            case DOWN: {
                return rotated.set(pos.getZ(), pos.getY(), dimensions.getZ() - 1 - pos.getX());
            }
            case UP: {
                return rotated.set(dimensions.getX() - 1 - pos.getZ(), pos.getY(), pos.getX());
            }
            case NORTH: {
                return rotated.set(dimensions.getX() - 1 - pos.getY(), pos.getX(), pos.getZ());
            }
            case SOUTH: {
                return rotated.set(pos.getY(), dimensions.getY() - 1 - pos.getX(), pos.getZ());
            }
            case WEST: {
                return rotated.set(pos.getX(), dimensions.getY() - 1 - pos.getZ(), pos.getY());
            }
            case EAST: {
                return rotated.set(pos.getX(), pos.getZ(), dimensions.getZ() - 1 - pos.getY());
            }
            default: {
                throw new RotationException("'%s' is not a valid axis!", new Object[] { axis.getName() });
            }
        }
    }
    
    private IBlockState rotateBlock(final IBlockState blockState, final EnumFacing axisRotation, final boolean forced) throws RotationException {
        final IProperty propertyFacing = BlockStateHelper.getProperty(blockState, "facing");
        if (propertyFacing instanceof PropertyDirection) {
            final Comparable value = blockState.getValue(propertyFacing);
            if (value instanceof EnumFacing) {
                final EnumFacing facing = getRotatedFacing(axisRotation, (EnumFacing)value);
                if (propertyFacing.getAllowedValues().contains(facing)) {
                    return blockState.withProperty(propertyFacing, (Comparable)facing);
                }
            }
        }
        else if (propertyFacing instanceof PropertyEnum) {
            if (BlockLever.EnumOrientation.class.isAssignableFrom(propertyFacing.getValueClass())) {
                final BlockLever.EnumOrientation orientation = (BlockLever.EnumOrientation)blockState.getValue(propertyFacing);
                final BlockLever.EnumOrientation orientationRotated = getRotatedLeverFacing(axisRotation, orientation);
                if (propertyFacing.getAllowedValues().contains(orientationRotated)) {
                    return blockState.withProperty(propertyFacing, (Comparable)orientationRotated);
                }
            }
        }
        else if (propertyFacing != null) {
            Reference.logger.error("'{}': found 'facing' property with unknown type {}", Block.REGISTRY.getNameForObject((Object)blockState.getBlock()), (Object)propertyFacing.getClass().getSimpleName());
        }
        final IProperty propertyAxis = BlockStateHelper.getProperty(blockState, "axis");
        if (propertyAxis instanceof PropertyEnum) {
            if (EnumFacing.Axis.class.isAssignableFrom(propertyAxis.getValueClass())) {
                final EnumFacing.Axis axis = (EnumFacing.Axis)blockState.getValue(propertyAxis);
                final EnumFacing.Axis axisRotated = getRotatedAxis(axisRotation, axis);
                return blockState.withProperty(propertyAxis, (Comparable)axisRotated);
            }
            if (BlockLog.EnumAxis.class.isAssignableFrom(propertyAxis.getValueClass())) {
                final BlockLog.EnumAxis axis2 = (BlockLog.EnumAxis)blockState.getValue(propertyAxis);
                final BlockLog.EnumAxis axisRotated2 = getRotatedLogAxis(axisRotation, axis2);
                return blockState.withProperty(propertyAxis, (Comparable)axisRotated2);
            }
        }
        else if (propertyAxis != null) {
            Reference.logger.error("'{}': found 'axis' property with unknown type {}", Block.REGISTRY.getNameForObject((Object)blockState.getBlock()), (Object)propertyAxis.getClass().getSimpleName());
        }
        final IProperty propertyVariant = BlockStateHelper.getProperty(blockState, "variant");
        if (propertyVariant instanceof PropertyEnum && BlockQuartz.EnumType.class.isAssignableFrom(propertyVariant.getValueClass())) {
            final BlockQuartz.EnumType type = (BlockQuartz.EnumType)blockState.getValue(propertyVariant);
            final BlockQuartz.EnumType typeRotated = getRotatedQuartzType(axisRotation, type);
            return blockState.withProperty(propertyVariant, (Comparable)typeRotated);
        }
        if (!forced && (propertyFacing != null || propertyAxis != null)) {
            throw new RotationException("'%s' cannot be rotated around '%s'", new Object[] { Block.REGISTRY.getNameForObject((Object)blockState.getBlock()), axisRotation });
        }
        return blockState;
    }
    
    private static EnumFacing getRotatedFacing(final EnumFacing source, final EnumFacing side) {
        return RotationHelper.FACINGS[source.ordinal()][side.ordinal()];
    }
    
    private static EnumFacing.Axis getRotatedAxis(final EnumFacing source, final EnumFacing.Axis axis) {
        return RotationHelper.AXISES[source.getAxis().ordinal()][axis.ordinal()];
    }
    
    private static BlockLog.EnumAxis getRotatedLogAxis(final EnumFacing source, final BlockLog.EnumAxis axis) {
        return RotationHelper.AXISES_LOG[source.getAxis().ordinal()][axis.ordinal()];
    }
    
    private static BlockQuartz.EnumType getRotatedQuartzType(final EnumFacing source, final BlockQuartz.EnumType type) {
        return RotationHelper.AXISES_QUARTZ[source.getAxis().ordinal()][type.ordinal()];
    }
    
    private static BlockLever.EnumOrientation getRotatedLeverFacing(final EnumFacing source, final BlockLever.EnumOrientation side) {
        EnumFacing facing;
        if (source.getAxis().isVertical() && side.getFacing().getAxis().isVertical()) {
            facing = ((side == BlockLever.EnumOrientation.UP_X || side == BlockLever.EnumOrientation.DOWN_X) ? EnumFacing.NORTH : EnumFacing.WEST);
        }
        else {
            facing = side.getFacing();
        }
        final EnumFacing facingRotated = getRotatedFacing(source, side.getFacing());
        return BlockLever.EnumOrientation.forFacings(facingRotated, facing);
    }
    
    static {
        INSTANCE = new RotationHelper();
        FACINGS = new EnumFacing[EnumFacing.VALUES.length][];
        AXISES = new EnumFacing.Axis[EnumFacing.Axis.values().length][];
        AXISES_LOG = new BlockLog.EnumAxis[EnumFacing.Axis.values().length][];
        AXISES_QUARTZ = new BlockQuartz.EnumType[EnumFacing.Axis.values().length][];
        RotationHelper.FACINGS[EnumFacing.DOWN.ordinal()] = new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.WEST, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.NORTH };
        RotationHelper.FACINGS[EnumFacing.UP.ordinal()] = new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH };
        RotationHelper.FACINGS[EnumFacing.NORTH.ordinal()] = new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.DOWN, EnumFacing.UP };
        RotationHelper.FACINGS[EnumFacing.SOUTH.ordinal()] = new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.UP, EnumFacing.DOWN };
        RotationHelper.FACINGS[EnumFacing.WEST.ordinal()] = new EnumFacing[] { EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.UP, EnumFacing.DOWN, EnumFacing.WEST, EnumFacing.EAST };
        RotationHelper.FACINGS[EnumFacing.EAST.ordinal()] = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.DOWN, EnumFacing.UP, EnumFacing.WEST, EnumFacing.EAST };
        RotationHelper.AXISES[EnumFacing.Axis.X.ordinal()] = new EnumFacing.Axis[] { EnumFacing.Axis.X, EnumFacing.Axis.Z, EnumFacing.Axis.Y };
        RotationHelper.AXISES[EnumFacing.Axis.Y.ordinal()] = new EnumFacing.Axis[] { EnumFacing.Axis.Z, EnumFacing.Axis.Y, EnumFacing.Axis.X };
        RotationHelper.AXISES[EnumFacing.Axis.Z.ordinal()] = new EnumFacing.Axis[] { EnumFacing.Axis.Y, EnumFacing.Axis.X, EnumFacing.Axis.Z };
        RotationHelper.AXISES_LOG[EnumFacing.Axis.X.ordinal()] = new BlockLog.EnumAxis[] { BlockLog.EnumAxis.X, BlockLog.EnumAxis.Z, BlockLog.EnumAxis.Y, BlockLog.EnumAxis.NONE };
        RotationHelper.AXISES_LOG[EnumFacing.Axis.Y.ordinal()] = new BlockLog.EnumAxis[] { BlockLog.EnumAxis.Z, BlockLog.EnumAxis.Y, BlockLog.EnumAxis.X, BlockLog.EnumAxis.NONE };
        RotationHelper.AXISES_LOG[EnumFacing.Axis.Z.ordinal()] = new BlockLog.EnumAxis[] { BlockLog.EnumAxis.Y, BlockLog.EnumAxis.X, BlockLog.EnumAxis.Z, BlockLog.EnumAxis.NONE };
        RotationHelper.AXISES_QUARTZ[EnumFacing.Axis.X.ordinal()] = new BlockQuartz.EnumType[] { BlockQuartz.EnumType.DEFAULT, BlockQuartz.EnumType.CHISELED, BlockQuartz.EnumType.LINES_Z, BlockQuartz.EnumType.LINES_X, BlockQuartz.EnumType.LINES_Y };
        RotationHelper.AXISES_QUARTZ[EnumFacing.Axis.Y.ordinal()] = new BlockQuartz.EnumType[] { BlockQuartz.EnumType.DEFAULT, BlockQuartz.EnumType.CHISELED, BlockQuartz.EnumType.LINES_Y, BlockQuartz.EnumType.LINES_Z, BlockQuartz.EnumType.LINES_X };
        RotationHelper.AXISES_QUARTZ[EnumFacing.Axis.Z.ordinal()] = new BlockQuartz.EnumType[] { BlockQuartz.EnumType.DEFAULT, BlockQuartz.EnumType.CHISELED, BlockQuartz.EnumType.LINES_X, BlockQuartz.EnumType.LINES_Y, BlockQuartz.EnumType.LINES_Z };
    }
    
    public static class RotationException extends Exception
    {
        public RotationException(final String message, final Object... args) {
            super(String.format(message, args));
        }
    }
}
