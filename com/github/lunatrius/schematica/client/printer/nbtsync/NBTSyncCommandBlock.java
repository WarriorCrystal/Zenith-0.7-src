//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.printer.nbtsync;

import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketCustomPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.Unpooled;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;

public class NBTSyncCommandBlock extends NBTSync
{
    @Override
    public boolean execute(final EntityPlayer player, final World schematic, final BlockPos pos, final World mcWorld, final BlockPos mcPos) {
        final TileEntity tileEntity = schematic.getTileEntity(pos);
        final TileEntity mcTileEntity = mcWorld.getTileEntity(mcPos);
        if (tileEntity instanceof TileEntityCommandBlock && mcTileEntity instanceof TileEntityCommandBlock) {
            final CommandBlockBaseLogic commandBlockLogic = ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic();
            final CommandBlockBaseLogic mcCommandBlockLogic = ((TileEntityCommandBlock)mcTileEntity).getCommandBlockLogic();
            if (!commandBlockLogic.getCommand().equals(mcCommandBlockLogic.getCommand())) {
                final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
                packetBuffer.writeByte(mcCommandBlockLogic.getCommandBlockType());
                mcCommandBlockLogic.fillInInfo((ByteBuf)packetBuffer);
                packetBuffer.writeString(commandBlockLogic.getCommand());
                packetBuffer.writeBoolean(mcCommandBlockLogic.shouldTrackOutput());
                return this.sendPacket((net.minecraft.network.Packet<INetHandler>)new CPacketCustomPayload("MC|AdvCdm", packetBuffer));
            }
        }
        return false;
    }
}
