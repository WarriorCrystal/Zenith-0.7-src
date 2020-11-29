// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.api;

import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public interface ISchematic
{
    IBlockState getBlockState(final BlockPos p0);
    
    boolean setBlockState(final BlockPos p0, final IBlockState p1);
    
    TileEntity getTileEntity(final BlockPos p0);
    
    List<TileEntity> getTileEntities();
    
    void setTileEntity(final BlockPos p0, final TileEntity p1);
    
    void removeTileEntity(final BlockPos p0);
    
    List<Entity> getEntities();
    
    void addEntity(final Entity p0);
    
    void removeEntity(final Entity p0);
    
    ItemStack getIcon();
    
    void setIcon(final ItemStack p0);
    
    int getWidth();
    
    int getLength();
    
    int getHeight();
    
    @Nonnull
    String getAuthor();
    
    void setAuthor(@Nonnull final String p0);
}
