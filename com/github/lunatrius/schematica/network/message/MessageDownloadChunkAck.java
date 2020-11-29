//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.network.message;

import net.minecraft.entity.player.EntityPlayerMP;
import com.github.lunatrius.schematica.handler.DownloadHandler;
import com.github.lunatrius.schematica.network.transfer.SchematicTransfer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageDownloadChunkAck implements IMessage, IMessageHandler<MessageDownloadChunkAck, IMessage>
{
    private int baseX;
    private int baseY;
    private int baseZ;
    
    public MessageDownloadChunkAck() {
    }
    
    public MessageDownloadChunkAck(final int baseX, final int baseY, final int baseZ) {
        this.baseX = baseX;
        this.baseY = baseY;
        this.baseZ = baseZ;
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.baseX = buf.readShort();
        this.baseY = buf.readShort();
        this.baseZ = buf.readShort();
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeShort(this.baseX);
        buf.writeShort(this.baseY);
        buf.writeShort(this.baseZ);
    }
    
    public IMessage onMessage(final MessageDownloadChunkAck message, final MessageContext ctx) {
        final EntityPlayerMP player = ctx.getServerHandler().player;
        final SchematicTransfer transfer = DownloadHandler.INSTANCE.transferMap.get(player);
        if (transfer != null) {
            transfer.confirmChunk(message.baseX, message.baseY, message.baseZ);
        }
        return null;
    }
}
