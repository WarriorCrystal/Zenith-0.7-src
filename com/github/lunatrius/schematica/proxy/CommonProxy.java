//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.proxy;

import com.github.lunatrius.schematica.world.chunk.SchematicContainer;
import com.github.lunatrius.schematica.world.storage.Schematic;
import com.github.lunatrius.schematica.world.schematic.SchematicUtil;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Iterator;
import java.util.List;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import com.github.lunatrius.schematica.nbt.NBTConversionException;
import net.minecraft.init.Blocks;
import com.github.lunatrius.schematica.nbt.NBTHelper;
import net.minecraft.util.math.BlockPos;
import com.github.lunatrius.core.util.math.MBlockPos;
import net.minecraft.world.World;
import com.github.lunatrius.schematica.api.ISchematic;
import java.io.IOException;
import java.io.File;
import com.github.lunatrius.schematica.command.CommandSchematicaRemove;
import com.github.lunatrius.schematica.command.CommandSchematicaList;
import net.minecraft.command.ICommand;
import com.github.lunatrius.schematica.command.CommandSchematicaSave;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import com.github.lunatrius.schematica.handler.DownloadHandler;
import com.github.lunatrius.schematica.handler.QueueTickHandler;
import net.minecraftforge.common.MinecraftForge;
import com.github.lunatrius.schematica.network.PacketHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public abstract class CommonProxy
{
    public boolean isSaveEnabled;
    public boolean isLoadEnabled;
    
    public CommonProxy() {
        this.isSaveEnabled = true;
        this.isLoadEnabled = true;
    }
    
    public void preInit(final FMLPreInitializationEvent event) {
        Reference.logger = event.getModLog();
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        FMLInterModComms.sendMessage("LunatriusCore", "checkUpdate", "1.12.2-14.23.0.2531");
    }
    
    public void init(final FMLInitializationEvent event) {
        PacketHandler.init();
        MinecraftForge.EVENT_BUS.register((Object)QueueTickHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)DownloadHandler.INSTANCE);
    }
    
    public void postInit(final FMLPostInitializationEvent event) {
    }
    
    public void serverStarting(final FMLServerStartingEvent event) {
        event.registerServerCommand((ICommand)new CommandSchematicaSave());
        event.registerServerCommand((ICommand)new CommandSchematicaList());
        event.registerServerCommand((ICommand)new CommandSchematicaRemove());
    }
    
    public void createFolders() {
        if (!ConfigurationHandler.schematicDirectory.exists() && !ConfigurationHandler.schematicDirectory.mkdirs()) {
            Reference.logger.warn("Could not create schematic directory [{}]!", (Object)ConfigurationHandler.schematicDirectory.getAbsolutePath());
        }
    }
    
    public abstract File getDataDirectory();
    
    public File getDirectory(final String directory) {
        final File dataDirectory = this.getDataDirectory();
        final File subDirectory = new File(dataDirectory, directory);
        if (!subDirectory.exists() && !subDirectory.mkdirs()) {
            Reference.logger.error("Could not create directory [{}]!", (Object)subDirectory.getAbsolutePath());
        }
        try {
            return subDirectory.getCanonicalFile();
        }
        catch (IOException e) {
            e.printStackTrace();
            return subDirectory;
        }
    }
    
    public void resetSettings() {
        this.isSaveEnabled = true;
        this.isLoadEnabled = true;
    }
    
    public void unloadSchematic() {
    }
    
    public void copyChunkToSchematic(final ISchematic schematic, final World world, final int chunkX, final int chunkZ, final int minX, final int maxX, final int minY, final int maxY, final int minZ, final int maxZ) {
        final MBlockPos pos = new MBlockPos();
        final MBlockPos localPos = new MBlockPos();
        final int localMinX = (minX < chunkX << 4) ? 0 : (minX & 0xF);
        final int localMaxX = (maxX > (chunkX << 4) + 15) ? 15 : (maxX & 0xF);
        final int localMinZ = (minZ < chunkZ << 4) ? 0 : (minZ & 0xF);
        final int localMaxZ = (maxZ > (chunkZ << 4) + 15) ? 15 : (maxZ & 0xF);
        for (int chunkLocalX = localMinX; chunkLocalX <= localMaxX; ++chunkLocalX) {
            for (int chunkLocalZ = localMinZ; chunkLocalZ <= localMaxZ; ++chunkLocalZ) {
                for (int y = minY; y <= maxY; ++y) {
                    final int x = chunkLocalX | chunkX << 4;
                    final int z = chunkLocalZ | chunkZ << 4;
                    final int localX = x - minX;
                    final int localY = y - minY;
                    final int localZ = z - minZ;
                    pos.set(x, y, z);
                    localPos.set(localX, localY, localZ);
                    try {
                        final IBlockState blockState = world.getBlockState((BlockPos)pos);
                        final Block block = blockState.getBlock();
                        final boolean success = schematic.setBlockState(localPos, blockState);
                        if (success && block.hasTileEntity(blockState)) {
                            final TileEntity tileEntity = world.getTileEntity((BlockPos)pos);
                            if (tileEntity != null) {
                                try {
                                    final TileEntity reloadedTileEntity = NBTHelper.reloadTileEntity(tileEntity, minX, minY, minZ);
                                    schematic.setTileEntity(localPos, reloadedTileEntity);
                                }
                                catch (NBTConversionException nce) {
                                    Reference.logger.error("Error while trying to save tile entity '{}'!", (Object)tileEntity, (Object)nce);
                                    schematic.setBlockState(localPos, Blocks.BEDROCK.getDefaultState());
                                }
                            }
                        }
                    }
                    catch (Exception e) {
                        Reference.logger.error("Something went wrong!", (Throwable)e);
                    }
                }
            }
        }
        final int minX2 = localMinX | chunkX << 4;
        final int minZ2 = localMinZ | chunkZ << 4;
        final int maxX2 = localMaxX | chunkX << 4;
        final int maxZ2 = localMaxZ | chunkZ << 4;
        final AxisAlignedBB bb = new AxisAlignedBB((double)minX2, (double)minY, (double)minZ2, (double)(maxX2 + 1), (double)(maxY + 1), (double)(maxZ2 + 1));
        final List<Entity> entities = (List<Entity>)world.getEntitiesWithinAABB((Class)Entity.class, bb);
        for (final Entity entity : entities) {
            try {
                final Entity reloadedEntity = NBTHelper.reloadEntity(entity, minX, minY, minZ);
                schematic.addEntity(reloadedEntity);
            }
            catch (NBTConversionException nce2) {
                Reference.logger.error("Error while trying to save entity '{}'!", (Object)entity, (Object)nce2);
            }
        }
    }
    
    public boolean saveSchematic(final EntityPlayer player, final File directory, String filename, final World world, @Nullable final String format, final BlockPos from, final BlockPos to) {
        try {
            String iconName = "";
            try {
                final String[] parts = filename.split(";");
                if (parts.length == 2) {
                    iconName = parts[0];
                    filename = parts[1];
                }
            }
            catch (Exception e) {
                Reference.logger.error("Failed to parse icon data!", (Throwable)e);
            }
            final int minX = Math.min(from.getX(), to.getX());
            final int maxX = Math.max(from.getX(), to.getX());
            final int minY = Math.min(from.getY(), to.getY());
            final int maxY = Math.max(from.getY(), to.getY());
            final int minZ = Math.min(from.getZ(), to.getZ());
            final int maxZ = Math.max(from.getZ(), to.getZ());
            final short width = (short)(Math.abs(maxX - minX) + 1);
            final short height = (short)(Math.abs(maxY - minY) + 1);
            final short length = (short)(Math.abs(maxZ - minZ) + 1);
            final ISchematic schematic = new Schematic(SchematicUtil.getIconFromName(iconName), width, height, length, player.getName());
            final SchematicContainer container = new SchematicContainer(schematic, player, world, new File(directory, filename), format, minX, maxX, minY, maxY, minZ, maxZ);
            QueueTickHandler.INSTANCE.queueSchematic(container);
            return true;
        }
        catch (Exception e2) {
            Reference.logger.error("Failed to save schematic!", (Throwable)e2);
            return false;
        }
    }
    
    public abstract boolean loadSchematic(final EntityPlayer p0, final File p1, final String p2);
    
    public abstract boolean isPlayerQuotaExceeded(final EntityPlayer p0);
    
    public abstract File getPlayerSchematicDirectory(final EntityPlayer p0, final boolean p1);
}
