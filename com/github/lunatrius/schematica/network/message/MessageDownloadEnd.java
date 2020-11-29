//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.network.message;

import java.io.File;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.client.Minecraft;
import com.github.lunatrius.schematica.world.schematic.SchematicFormat;
import com.github.lunatrius.schematica.handler.DownloadHandler;
import net.minecraft.entity.player.EntityPlayer;
import com.github.lunatrius.schematica.Schematica;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageDownloadEnd implements IMessage, IMessageHandler<MessageDownloadEnd, IMessage>
{
    public String name;
    
    public MessageDownloadEnd() {
    }
    
    public MessageDownloadEnd(final String name) {
        this.name = name;
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.name = ByteBufUtils.readUTF8String(buf);
    }
    
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.name);
    }
    
    public IMessage onMessage(final MessageDownloadEnd message, final MessageContext ctx) {
        final File directory = Schematica.proxy.getPlayerSchematicDirectory(null, true);
        final boolean success = SchematicFormat.writeToFile(directory, message.name, null, DownloadHandler.INSTANCE.schematic);
        if (success) {
            Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentTranslation("schematica.command.download.downloadSucceeded", new Object[] { message.name }));
        }
        DownloadHandler.INSTANCE.schematic = null;
        return null;
    }
}
