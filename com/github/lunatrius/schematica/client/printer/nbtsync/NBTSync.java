//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.printer.nbtsync;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;

public abstract class NBTSync
{
    protected final Minecraft minecraft;
    
    public NBTSync() {
        this.minecraft = Minecraft.getMinecraft();
    }
    
    public abstract boolean execute(final EntityPlayer p0, final World p1, final BlockPos p2, final World p3, final BlockPos p4);
    
    public final <T extends INetHandler> boolean sendPacket(final Packet<T> packet) {
        final NetHandlerPlayClient connection = this.minecraft.getConnection();
        if (connection == null) {
            return false;
        }
        connection.sendPacket((Packet)packet);
        return true;
    }
}
