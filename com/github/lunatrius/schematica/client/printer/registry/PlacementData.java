// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.printer.registry;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.util.EnumFacing;
import java.util.List;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.state.IBlockState;

public class PlacementData
{
    private final IValidPlayerFacing validPlayerFacing;
    private final IValidBlockFacing validBlockFacing;
    private IOffset offsetX;
    private IOffset offsetY;
    private IOffset offsetZ;
    private IExtraClick extraClick;
    
    public PlacementData() {
        this(null, null);
    }
    
    public PlacementData(final IValidPlayerFacing validPlayerFacing) {
        this(validPlayerFacing, null);
    }
    
    public PlacementData(final IValidBlockFacing validBlockFacing) {
        this(null, validBlockFacing);
    }
    
    public PlacementData(final IValidPlayerFacing validPlayerFacing, final IValidBlockFacing validBlockFacing) {
        this.validPlayerFacing = validPlayerFacing;
        this.validBlockFacing = validBlockFacing;
        this.offsetX = null;
        this.offsetY = null;
        this.offsetZ = null;
    }
    
    public PlacementData setOffsetX(final IOffset offset) {
        this.offsetX = offset;
        return this;
    }
    
    public PlacementData setOffsetY(final IOffset offset) {
        this.offsetY = offset;
        return this;
    }
    
    public PlacementData setOffsetZ(final IOffset offset) {
        this.offsetZ = offset;
        return this;
    }
    
    public PlacementData setExtraClick(final IExtraClick extraClick) {
        this.extraClick = extraClick;
        return this;
    }
    
    public float getOffsetX(final IBlockState blockState) {
        if (this.offsetX != null) {
            return this.offsetX.getOffset(blockState);
        }
        return 0.5f;
    }
    
    public float getOffsetY(final IBlockState blockState) {
        if (this.offsetY != null) {
            return this.offsetY.getOffset(blockState);
        }
        return 0.5f;
    }
    
    public float getOffsetZ(final IBlockState blockState) {
        if (this.offsetZ != null) {
            return this.offsetZ.getOffset(blockState);
        }
        return 0.5f;
    }
    
    public int getExtraClicks(final IBlockState blockState) {
        if (this.extraClick != null) {
            return this.extraClick.getExtraClicks(blockState);
        }
        return 0;
    }
    
    public boolean isValidPlayerFacing(final IBlockState blockState, final EntityPlayer player, final BlockPos pos, final World world) {
        return this.validPlayerFacing == null || this.validPlayerFacing.isValid(blockState, player, pos, world);
    }
    
    public List<EnumFacing> getValidBlockFacings(final List<EnumFacing> solidSides, final IBlockState blockState) {
        final List<EnumFacing> list = (this.validBlockFacing != null) ? this.validBlockFacing.getValidBlockFacings(solidSides, blockState) : new ArrayList<EnumFacing>(solidSides);
        final Iterator<EnumFacing> iterator = list.iterator();
        while (iterator.hasNext()) {
            final EnumFacing facing = iterator.next();
            if (this.offsetY != null) {
                final float offset = this.offsetY.getOffset(blockState);
                if (offset < 0.5 && facing == EnumFacing.UP) {
                    iterator.remove();
                }
                else {
                    if (offset <= 0.5 || facing != EnumFacing.DOWN) {
                        continue;
                    }
                    iterator.remove();
                }
            }
        }
        return list;
    }
}
