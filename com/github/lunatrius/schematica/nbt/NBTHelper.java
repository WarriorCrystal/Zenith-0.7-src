//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.nbt;

import net.minecraft.entity.EntityList;
import com.github.lunatrius.schematica.world.WorldDummy;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import java.util.Iterator;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import java.util.ArrayList;
import net.minecraft.tileentity.TileEntity;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper
{
    public static List<TileEntity> readTileEntitiesFromCompound(final NBTTagCompound compound) {
        return readTileEntitiesFromCompound(compound, new ArrayList<TileEntity>());
    }
    
    public static List<TileEntity> readTileEntitiesFromCompound(final NBTTagCompound compound, final List<TileEntity> tileEntities) {
        final NBTTagList tagList = compound.getTagList("TileEntities", 10);
        for (int i = 0; i < tagList.tagCount(); ++i) {
            final NBTTagCompound tileEntityCompound = tagList.getCompoundTagAt(i);
            final TileEntity tileEntity = readTileEntityFromCompound(tileEntityCompound);
            tileEntities.add(tileEntity);
        }
        return tileEntities;
    }
    
    public static NBTTagCompound writeTileEntitiesToCompound(final List<TileEntity> tileEntities) {
        return writeTileEntitiesToCompound(tileEntities, new NBTTagCompound());
    }
    
    public static NBTTagCompound writeTileEntitiesToCompound(final List<TileEntity> tileEntities, final NBTTagCompound compound) {
        final NBTTagList tagList = new NBTTagList();
        for (final TileEntity tileEntity : tileEntities) {
            final NBTTagCompound tileEntityCompound = writeTileEntityToCompound(tileEntity);
            tagList.appendTag((NBTBase)tileEntityCompound);
        }
        compound.setTag("TileEntities", (NBTBase)tagList);
        return compound;
    }
    
    public static List<Entity> readEntitiesFromCompound(final NBTTagCompound compound) {
        return readEntitiesFromCompound(compound, null, new ArrayList<Entity>());
    }
    
    public static List<Entity> readEntitiesFromCompound(final NBTTagCompound compound, final World world) {
        return readEntitiesFromCompound(compound, world, new ArrayList<Entity>());
    }
    
    public static List<Entity> readEntitiesFromCompound(final NBTTagCompound compound, final List<Entity> entities) {
        return readEntitiesFromCompound(compound, null, entities);
    }
    
    public static List<Entity> readEntitiesFromCompound(final NBTTagCompound compound, final World world, final List<Entity> entities) {
        final NBTTagList tagList = compound.getTagList("Entities", 10);
        for (int i = 0; i < tagList.tagCount(); ++i) {
            final NBTTagCompound entityCompound = tagList.getCompoundTagAt(i);
            final Entity entity = readEntityFromCompound(entityCompound, world);
            if (entity != null) {
                entities.add(entity);
            }
        }
        return entities;
    }
    
    public static NBTTagCompound writeEntitiesToCompound(final List<Entity> entities) {
        return writeEntitiesToCompound(entities, new NBTTagCompound());
    }
    
    public static NBTTagCompound writeEntitiesToCompound(final List<Entity> entities, final NBTTagCompound compound) {
        final NBTTagList tagList = new NBTTagList();
        for (final Entity entity : entities) {
            final NBTTagCompound entityCompound = new NBTTagCompound();
            entity.writeToNBT(entityCompound);
            tagList.appendTag((NBTBase)entityCompound);
        }
        compound.setTag("Entities", (NBTBase)tagList);
        return compound;
    }
    
    public static TileEntity reloadTileEntity(final TileEntity tileEntity) throws NBTConversionException {
        return reloadTileEntity(tileEntity, 0, 0, 0);
    }
    
    public static TileEntity reloadTileEntity(TileEntity tileEntity, final int offsetX, final int offsetY, final int offsetZ) throws NBTConversionException {
        if (tileEntity == null) {
            return null;
        }
        try {
            final NBTTagCompound tileEntityCompound = writeTileEntityToCompound(tileEntity);
            tileEntity = readTileEntityFromCompound(tileEntityCompound);
            final BlockPos pos = tileEntity.getPos();
            tileEntity.setPos(pos.add(-offsetX, -offsetY, -offsetZ));
        }
        catch (Throwable t) {
            throw new NBTConversionException(tileEntity, t);
        }
        return tileEntity;
    }
    
    public static Entity reloadEntity(final Entity entity) throws NBTConversionException {
        return reloadEntity(entity, 0, 0, 0);
    }
    
    public static Entity reloadEntity(Entity entity, final int offsetX, final int offsetY, final int offsetZ) throws NBTConversionException {
        if (entity == null) {
            return null;
        }
        try {
            final NBTTagCompound entityCompound = writeEntityToCompound(entity);
            if (entityCompound != null) {
                entity = readEntityFromCompound(entityCompound, WorldDummy.instance());
                if (entity != null) {
                    final Entity entity2 = entity;
                    entity2.posX -= offsetX;
                    final Entity entity3 = entity;
                    entity3.posY -= offsetY;
                    final Entity entity4 = entity;
                    entity4.posZ -= offsetZ;
                }
            }
        }
        catch (Throwable t) {
            throw new NBTConversionException(entity, t);
        }
        return entity;
    }
    
    public static NBTTagCompound writeTileEntityToCompound(final TileEntity tileEntity) {
        final NBTTagCompound tileEntityCompound = new NBTTagCompound();
        tileEntity.writeToNBT(tileEntityCompound);
        return tileEntityCompound;
    }
    
    public static TileEntity readTileEntityFromCompound(final NBTTagCompound tileEntityCompound) {
        return TileEntity.create((World)null, tileEntityCompound);
    }
    
    public static NBTTagCompound writeEntityToCompound(final Entity entity) {
        final NBTTagCompound entityCompound = new NBTTagCompound();
        if (entity.writeToNBTOptional(entityCompound)) {
            return entityCompound;
        }
        return null;
    }
    
    public static Entity readEntityFromCompound(final NBTTagCompound nbtTagCompound, final World world) {
        return EntityList.createEntityFromNBT(nbtTagCompound, world);
    }
}
