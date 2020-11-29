//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.world.storage;

import net.minecraft.entity.player.EntityPlayer;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import java.util.List;
import net.minecraft.item.ItemStack;
import com.github.lunatrius.schematica.api.ISchematic;

public class Schematic implements ISchematic
{
    private static final ItemStack DEFAULT_ICON;
    private ItemStack icon;
    private final short[][][] blocks;
    private final byte[][][] metadata;
    private final List<TileEntity> tileEntities;
    private final List<Entity> entities;
    private final int width;
    private final int height;
    private final int length;
    private String author;
    
    public Schematic(final ItemStack icon, final int width, final int height, final int length) {
        this(icon, width, height, length, "");
    }
    
    public Schematic(final ItemStack icon, final int width, final int height, final int length, @Nonnull final String author) {
        this.tileEntities = new ArrayList<TileEntity>();
        this.entities = new ArrayList<Entity>();
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        this.icon = icon;
        this.blocks = new short[width][height][length];
        this.metadata = new byte[width][height][length];
        this.width = width;
        this.height = height;
        this.length = length;
        this.author = author;
    }
    
    @Override
    public IBlockState getBlockState(final BlockPos pos) {
        if (!this.isValid(pos)) {
            return Blocks.AIR.getDefaultState();
        }
        final int x = pos.getX();
        final int y = pos.getY();
        final int z = pos.getZ();
        final Block block = (Block)Block.REGISTRY.getObjectById((int)this.blocks[x][y][z]);
        return block.getStateFromMeta((int)this.metadata[x][y][z]);
    }
    
    @Override
    public boolean setBlockState(final BlockPos pos, final IBlockState blockState) {
        if (!this.isValid(pos)) {
            return false;
        }
        final Block block = blockState.getBlock();
        final int id = Block.REGISTRY.getIDForObject((Object)block);
        if (id == -1) {
            return false;
        }
        final int meta = block.getMetaFromState(blockState);
        final int x = pos.getX();
        final int y = pos.getY();
        final int z = pos.getZ();
        this.blocks[x][y][z] = (short)id;
        this.metadata[x][y][z] = (byte)meta;
        return true;
    }
    
    @Override
    public TileEntity getTileEntity(final BlockPos pos) {
        for (final TileEntity tileEntity : this.tileEntities) {
            if (tileEntity.getPos().equals((Object)pos)) {
                return tileEntity;
            }
        }
        return null;
    }
    
    @Override
    public List<TileEntity> getTileEntities() {
        return this.tileEntities;
    }
    
    @Override
    public void setTileEntity(final BlockPos pos, final TileEntity tileEntity) {
        if (!this.isValid(pos)) {
            return;
        }
        this.removeTileEntity(pos);
        if (tileEntity != null) {
            this.tileEntities.add(tileEntity);
        }
    }
    
    @Override
    public void removeTileEntity(final BlockPos pos) {
        final Iterator<TileEntity> iterator = this.tileEntities.iterator();
        while (iterator.hasNext()) {
            final TileEntity tileEntity = iterator.next();
            if (tileEntity.getPos().equals((Object)pos)) {
                iterator.remove();
            }
        }
    }
    
    @Override
    public List<Entity> getEntities() {
        return this.entities;
    }
    
    @Override
    public void addEntity(final Entity entity) {
        if (entity == null || entity.getUniqueID() == null || entity instanceof EntityPlayer) {
            return;
        }
        for (final Entity e : this.entities) {
            if (entity.getUniqueID().equals(e.getUniqueID())) {
                return;
            }
        }
        this.entities.add(entity);
    }
    
    @Override
    public void removeEntity(final Entity entity) {
        if (entity == null || entity.getUniqueID() == null) {
            return;
        }
        final Iterator<Entity> iterator = this.entities.iterator();
        while (iterator.hasNext()) {
            final Entity e = iterator.next();
            if (entity.getUniqueID().equals(e.getUniqueID())) {
                iterator.remove();
            }
        }
    }
    
    @Override
    public ItemStack getIcon() {
        return this.icon;
    }
    
    @Override
    public void setIcon(final ItemStack icon) {
        if (icon != null) {
            this.icon = icon;
        }
        else {
            this.icon = Schematic.DEFAULT_ICON.copy();
        }
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public int getLength() {
        return this.length;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    private boolean isValid(final BlockPos pos) {
        final int x = pos.getX();
        final int y = pos.getY();
        final int z = pos.getZ();
        return x >= 0 && y >= 0 && z >= 0 && x < this.width && y < this.height && z < this.length;
    }
    
    @Nonnull
    @Override
    public String getAuthor() {
        return this.author;
    }
    
    @Override
    public void setAuthor(@Nonnull final String author) {
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        this.author = author;
    }
    
    static {
        DEFAULT_ICON = new ItemStack((Block)Blocks.GRASS);
    }
}
