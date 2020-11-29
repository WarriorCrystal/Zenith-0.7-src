//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.printer.nbtsync;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUpdateSign;
import java.util.Arrays;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;

public class NBTSyncSign extends NBTSync
{
    @Override
    public boolean execute(final EntityPlayer player, final World schematic, final BlockPos pos, final World mcWorld, final BlockPos mcPos) {
        final TileEntity tileEntity = schematic.getTileEntity(pos);
        final TileEntity mcTileEntity = mcWorld.getTileEntity(mcPos);
        if (tileEntity instanceof TileEntitySign && mcTileEntity instanceof TileEntitySign) {
            final ITextComponent[] signText = ((TileEntitySign)tileEntity).signText;
            final ITextComponent[] mcSignText = ((TileEntitySign)mcTileEntity).signText;
            if (!Arrays.equals(signText, mcSignText)) {
                return this.sendPacket((net.minecraft.network.Packet<INetHandler>)new CPacketUpdateSign(mcPos, signText));
            }
        }
        return false;
    }
}
