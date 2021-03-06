// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.network.message;

import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.client.printer.SchematicPrinter;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageCapabilities implements IMessage, IMessageHandler<MessageCapabilities, IMessage>
{
    public boolean isPrinterEnabled;
    public boolean isSaveEnabled;
    public boolean isLoadEnabled;
    
    public MessageCapabilities() {
        this(false, false, false);
    }
    
    public MessageCapabilities(final boolean isPrinterEnabled, final boolean isSaveEnabled, final boolean isLoadEnabled) {
        this.isPrinterEnabled = isPrinterEnabled;
        this.isSaveEnabled = isSaveEnabled;
        this.isLoadEnabled = isLoadEnabled;
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.isPrinterEnabled = buf.readBoolean();
        this.isSaveEnabled = buf.readBoolean();
        this.isLoadEnabled = buf.readBoolean();
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeBoolean(this.isPrinterEnabled);
        buf.writeBoolean(this.isSaveEnabled);
        buf.writeBoolean(this.isLoadEnabled);
    }
    
    public IMessage onMessage(final MessageCapabilities message, final MessageContext ctx) {
        SchematicPrinter.INSTANCE.setEnabled(message.isPrinterEnabled);
        Schematica.proxy.isSaveEnabled = message.isSaveEnabled;
        Schematica.proxy.isLoadEnabled = message.isLoadEnabled;
        Reference.logger.info("Server capabilities{printer={}, save={}, load={}}", (Object)message.isPrinterEnabled, (Object)message.isSaveEnabled, (Object)message.isLoadEnabled);
        return null;
    }
}
