//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.network.message;

import com.github.lunatrius.schematica.handler.DownloadHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.nbt.NBTTagCompound;
import com.github.lunatrius.schematica.nbt.NBTHelper;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import java.util.Iterator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import com.github.lunatrius.core.util.math.MBlockPos;
import java.util.ArrayList;
import com.github.lunatrius.schematica.api.ISchematic;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import java.util.List;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageDownloadChunk implements IMessage, IMessageHandler<MessageDownloadChunk, IMessage>
{
    public int baseX;
    public int baseY;
    public int baseZ;
    public short[][][] blocks;
    public byte[][][] metadata;
    public List<TileEntity> tileEntities;
    public List<Entity> entities;
    
    public MessageDownloadChunk() {
    }
    
    public MessageDownloadChunk(final ISchematic schematic, final int baseX, final int baseY, final int baseZ) {
        this.baseX = baseX;
        this.baseY = baseY;
        this.baseZ = baseZ;
        this.blocks = new short[16][16][16];
        this.metadata = new byte[16][16][16];
        this.tileEntities = new ArrayList<TileEntity>();
        this.entities = new ArrayList<Entity>();
        final MBlockPos pos = new MBlockPos();
        for (int x = 0; x < 16; ++x) {
            for (int y = 0; y < 16; ++y) {
                for (int z = 0; z < 16; ++z) {
                    pos.set(baseX + x, baseY + y, baseZ + z);
                    final IBlockState blockState = schematic.getBlockState(pos);
                    final Block block = blockState.getBlock();
                    final int id = Block.REGISTRY.getIDForObject((Object)block);
                    this.blocks[x][y][z] = (short)id;
                    this.metadata[x][y][z] = (byte)block.getMetaFromState(blockState);
                    final TileEntity tileEntity = schematic.getTileEntity(pos);
                    if (tileEntity != null) {
                        this.tileEntities.add(tileEntity);
                    }
                }
            }
        }
    }
    
    private void copyToSchematic(final ISchematic schematic) {
        final MBlockPos pos = new MBlockPos();
        for (int x = 0; x < 16; ++x) {
            for (int y = 0; y < 16; ++y) {
                for (int z = 0; z < 16; ++z) {
                    final short id = this.blocks[x][y][z];
                    final byte meta = this.metadata[x][y][z];
                    final Block block = (Block)Block.REGISTRY.getObjectById((int)id);
                    pos.set(this.baseX + x, this.baseY + y, this.baseZ + z);
                    schematic.setBlockState(pos, block.getStateFromMeta((int)meta));
                }
            }
        }
        for (final TileEntity tileEntity : this.tileEntities) {
            schematic.setTileEntity(tileEntity.getPos(), tileEntity);
        }
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.baseX = buf.readShort();
        this.baseY = buf.readShort();
        this.baseZ = buf.readShort();
        this.blocks = new short[16][16][16];
        this.metadata = new byte[16][16][16];
        this.tileEntities = new ArrayList<TileEntity>();
        this.entities = new ArrayList<Entity>();
        for (int x = 0; x < 16; ++x) {
            for (int y = 0; y < 16; ++y) {
                for (int z = 0; z < 16; ++z) {
                    this.blocks[x][y][z] = buf.readShort();
                    this.metadata[x][y][z] = buf.readByte();
                }
            }
        }
        final NBTTagCompound compound = ByteBufUtils.readTag(buf);
        this.tileEntities = NBTHelper.readTileEntitiesFromCompound(compound, this.tileEntities);
        final NBTTagCompound compound2 = ByteBufUtils.readTag(buf);
        this.entities = NBTHelper.readEntitiesFromCompound(compound2, this.entities);
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeShort(this.baseX);
        buf.writeShort(this.baseY);
        buf.writeShort(this.baseZ);
        for (int x = 0; x < 16; ++x) {
            for (int y = 0; y < 16; ++y) {
                for (int z = 0; z < 16; ++z) {
                    buf.writeShort((int)this.blocks[x][y][z]);
                    buf.writeByte((int)this.metadata[x][y][z]);
                }
            }
        }
        final NBTTagCompound compound = NBTHelper.writeTileEntitiesToCompound(this.tileEntities);
        ByteBufUtils.writeTag(buf, compound);
        final NBTTagCompound compound2 = NBTHelper.writeEntitiesToCompound(this.entities);
        ByteBufUtils.writeTag(buf, compound2);
    }
    
    public IMessage onMessage(final MessageDownloadChunk message, final MessageContext ctx) {
        message.copyToSchematic(DownloadHandler.INSTANCE.schematic);
        return (IMessage)new MessageDownloadChunkAck(message.baseX, message.baseY, message.baseZ);
    }
}
