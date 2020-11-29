// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.network.message;

import com.github.lunatrius.schematica.world.storage.Schematic;
import com.github.lunatrius.schematica.handler.DownloadHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import com.github.lunatrius.schematica.api.ISchematic;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageDownloadBegin implements IMessage, IMessageHandler<MessageDownloadBegin, IMessage>
{
    public ItemStack icon;
    public int width;
    public int height;
    public int length;
    
    public MessageDownloadBegin() {
    }
    
    public MessageDownloadBegin(final ISchematic schematic) {
        this.icon = schematic.getIcon();
        this.width = schematic.getWidth();
        this.height = schematic.getHeight();
        this.length = schematic.getLength();
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.icon = ByteBufUtils.readItemStack(buf);
        this.width = buf.readShort();
        this.height = buf.readShort();
        this.length = buf.readShort();
    }
    
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.icon);
        buf.writeShort(this.width);
        buf.writeShort(this.height);
        buf.writeShort(this.length);
    }
    
    public IMessage onMessage(final MessageDownloadBegin message, final MessageContext ctx) {
        DownloadHandler.INSTANCE.schematic = new Schematic(message.icon, message.width, message.height, message.length);
        return (IMessage)new MessageDownloadBeginAck();
    }
}
