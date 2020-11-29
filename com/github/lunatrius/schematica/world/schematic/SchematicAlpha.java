//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.world.schematic;

import java.util.List;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import com.github.lunatrius.schematica.api.event.PreSchematicSaveEvent;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.block.state.IBlockState;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import net.minecraft.item.ItemStack;
import com.github.lunatrius.schematica.nbt.NBTHelper;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.util.math.BlockPos;
import com.github.lunatrius.schematica.world.storage.Schematic;
import com.github.lunatrius.core.util.math.MBlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.block.Block;
import java.util.HashMap;
import com.github.lunatrius.schematica.api.ISchematic;
import net.minecraft.nbt.NBTTagCompound;

public class SchematicAlpha extends SchematicFormat
{
    @Override
    public ISchematic readFromNBT(final NBTTagCompound tagCompound) {
        final ItemStack icon = SchematicUtil.getIconFromNBT(tagCompound);
        final byte[] localBlocks = tagCompound.getByteArray("Blocks");
        final byte[] localMetadata = tagCompound.getByteArray("Data");
        boolean extra = false;
        byte[] extraBlocks = null;
        byte[] extraBlocksNibble = null;
        if (tagCompound.hasKey("AddBlocks")) {
            extra = true;
            extraBlocksNibble = tagCompound.getByteArray("AddBlocks");
            extraBlocks = new byte[extraBlocksNibble.length * 2];
            for (int i = 0; i < extraBlocksNibble.length; ++i) {
                extraBlocks[i * 2 + 0] = (byte)(extraBlocksNibble[i] >> 4 & 0xF);
                extraBlocks[i * 2 + 1] = (byte)(extraBlocksNibble[i] & 0xF);
            }
        }
        else if (tagCompound.hasKey("Add")) {
            extra = true;
            extraBlocks = tagCompound.getByteArray("Add");
        }
        final short width = tagCompound.getShort("Width");
        final short length = tagCompound.getShort("Length");
        final short height = tagCompound.getShort("Height");
        Short id = null;
        final Map<Short, Short> oldToNew = new HashMap<Short, Short>();
        if (tagCompound.hasKey("SchematicaMapping")) {
            final NBTTagCompound mapping = tagCompound.getCompoundTag("SchematicaMapping");
            final Set<String> names = (Set<String>)mapping.getKeySet();
            for (final String name : names) {
                oldToNew.put(mapping.getShort(name), (short)Block.REGISTRY.getIDForObject(Block.REGISTRY.getObject((Object)new ResourceLocation(name))));
            }
        }
        final MBlockPos pos = new MBlockPos();
        final ISchematic schematic = new Schematic(icon, width, height, length);
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < length; ++z) {
                    final int index = x + (y * length + z) * width;
                    int blockID = (localBlocks[index] & 0xFF) | (extra ? ((extraBlocks[index] & 0xFF) << 8) : 0);
                    final int meta = localMetadata[index] & 0xFF;
                    if ((id = oldToNew.get((short)blockID)) != null) {
                        blockID = id;
                    }
                    final Block block = (Block)Block.REGISTRY.getObjectById(blockID);
                    pos.set(x, y, z);
                    try {
                        final IBlockState blockState = block.getStateFromMeta(meta);
                        schematic.setBlockState(pos, blockState);
                    }
                    catch (Exception e) {
                        Reference.logger.error("Could not set block state at {} to {} with metadata {}", (Object)pos, Block.REGISTRY.getNameForObject((Object)block), (Object)meta, (Object)e);
                    }
                }
            }
        }
        final NBTTagList tileEntitiesList = tagCompound.getTagList("TileEntities", 10);
        for (int j = 0; j < tileEntitiesList.tagCount(); ++j) {
            try {
                final TileEntity tileEntity = NBTHelper.readTileEntityFromCompound(tileEntitiesList.getCompoundTagAt(j));
                if (tileEntity != null) {
                    schematic.setTileEntity(tileEntity.getPos(), tileEntity);
                }
            }
            catch (Exception e2) {
                Reference.logger.error("TileEntity failed to load properly!", (Throwable)e2);
            }
        }
        return schematic;
    }
    
    @Override
    public boolean writeToNBT(final NBTTagCompound tagCompound, final ISchematic schematic) {
        final NBTTagCompound tagCompoundIcon = new NBTTagCompound();
        final ItemStack icon = schematic.getIcon();
        icon.writeToNBT(tagCompoundIcon);
        tagCompound.setTag("Icon", (NBTBase)tagCompoundIcon);
        tagCompound.setShort("Width", (short)schematic.getWidth());
        tagCompound.setShort("Length", (short)schematic.getLength());
        tagCompound.setShort("Height", (short)schematic.getHeight());
        final int size = schematic.getWidth() * schematic.getLength() * schematic.getHeight();
        final byte[] localBlocks = new byte[size];
        final byte[] localMetadata = new byte[size];
        final byte[] extraBlocks = new byte[size];
        final byte[] extraBlocksNibble = new byte[(int)Math.ceil(size / 2.0)];
        boolean extra = false;
        final MBlockPos pos = new MBlockPos();
        final Map<String, Short> mappings = new HashMap<String, Short>();
        for (int x = 0; x < schematic.getWidth(); ++x) {
            for (int y = 0; y < schematic.getHeight(); ++y) {
                for (int z = 0; z < schematic.getLength(); ++z) {
                    final int index = x + (y * schematic.getLength() + z) * schematic.getWidth();
                    final IBlockState blockState = schematic.getBlockState(pos.set(x, y, z));
                    final Block block = blockState.getBlock();
                    final int blockId = Block.REGISTRY.getIDForObject((Object)block);
                    localBlocks[index] = (byte)blockId;
                    localMetadata[index] = (byte)block.getMetaFromState(blockState);
                    if ((extraBlocks[index] = (byte)(blockId >> 8)) > 0) {
                        extra = true;
                    }
                    final String name = String.valueOf(Block.REGISTRY.getNameForObject((Object)block));
                    if (!mappings.containsKey(name)) {
                        mappings.put(name, (short)blockId);
                    }
                }
            }
        }
        int count = 20;
        final NBTTagList tileEntitiesList = new NBTTagList();
        for (final TileEntity tileEntity : schematic.getTileEntities()) {
            try {
                final NBTTagCompound tileEntityTagCompound = NBTHelper.writeTileEntityToCompound(tileEntity);
                tileEntitiesList.appendTag((NBTBase)tileEntityTagCompound);
            }
            catch (Exception e) {
                final BlockPos tePos = tileEntity.getPos();
                final int index2 = tePos.getX() + (tePos.getY() * schematic.getLength() + tePos.getZ()) * schematic.getWidth();
                if (--count > 0) {
                    final IBlockState blockState2 = schematic.getBlockState(tePos);
                    final Block block2 = blockState2.getBlock();
                    Reference.logger.error("Block {}[{}] with TileEntity {} failed to save! Replacing with bedrock...", (Object)block2, (block2 != null) ? Block.REGISTRY.getNameForObject((Object)block2) : "?", (Object)tileEntity.getClass().getName(), (Object)e);
                }
                localBlocks[index2] = (byte)Block.REGISTRY.getIDForObject((Object)Blocks.BEDROCK);
                extraBlocks[index2] = (localMetadata[index2] = 0);
            }
        }
        for (int i = 0; i < extraBlocksNibble.length; ++i) {
            if (i * 2 + 1 < extraBlocks.length) {
                extraBlocksNibble[i] = (byte)(extraBlocks[i * 2 + 0] << 4 | extraBlocks[i * 2 + 1]);
            }
            else {
                extraBlocksNibble[i] = (byte)(extraBlocks[i * 2 + 0] << 4);
            }
        }
        final NBTTagList entityList = new NBTTagList();
        final List<Entity> entities = schematic.getEntities();
        for (final Entity entity : entities) {
            try {
                final NBTTagCompound entityCompound = NBTHelper.writeEntityToCompound(entity);
                if (entityCompound == null) {
                    continue;
                }
                entityList.appendTag((NBTBase)entityCompound);
            }
            catch (Throwable t) {
                Reference.logger.error("Entity {} failed to save, skipping!", (Object)entity, (Object)t);
            }
        }
        final PreSchematicSaveEvent event = new PreSchematicSaveEvent(schematic, mappings);
        MinecraftForge.EVENT_BUS.post((Event)event);
        final NBTTagCompound nbtMapping = new NBTTagCompound();
        for (final Map.Entry<String, Short> entry : mappings.entrySet()) {
            nbtMapping.setShort((String)entry.getKey(), (short)entry.getValue());
        }
        tagCompound.setString("Materials", "Alpha");
        tagCompound.setByteArray("Blocks", localBlocks);
        tagCompound.setByteArray("Data", localMetadata);
        if (extra) {
            tagCompound.setByteArray("AddBlocks", extraBlocksNibble);
        }
        tagCompound.setTag("Entities", (NBTBase)entityList);
        tagCompound.setTag("TileEntities", (NBTBase)tileEntitiesList);
        tagCompound.setTag("SchematicaMapping", (NBTBase)nbtMapping);
        final NBTTagCompound extendedMetadata = event.extendedMetadata;
        if (!extendedMetadata.isEmpty()) {
            tagCompound.setTag("ExtendedMetadata", (NBTBase)extendedMetadata);
        }
        return true;
    }
    
    @Override
    public String getName() {
        return "schematica.format.alpha";
    }
    
    @Override
    public String getExtension() {
        return ".schematic";
    }
}
