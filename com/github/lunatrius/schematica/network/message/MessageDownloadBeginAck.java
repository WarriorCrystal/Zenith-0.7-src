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

public class MessageDownloadBeginAck implements IMessage, IMessageHandler<MessageDownloadBeginAck, IMessage>
{
    public void fromBytes(final ByteBuf buf) {
    }
    
    public void toBytes(final ByteBuf buf) {
    }
    
    public IMessage onMessage(final MessageDownloadBeginAck message, final MessageContext ctx) {
        final EntityPlayerMP player = ctx.getServerHandler().player;
        final SchematicTransfer transfer = DownloadHandler.INSTANCE.transferMap.get(player);
        if (transfer != null) {
            transfer.setState(SchematicTransfer.State.CHUNK_WAIT);
        }
        return null;
    }
}
