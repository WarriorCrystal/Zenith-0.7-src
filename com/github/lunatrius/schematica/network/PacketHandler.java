// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import com.github.lunatrius.schematica.network.message.MessageDownloadEnd;
import com.github.lunatrius.schematica.network.message.MessageDownloadChunkAck;
import com.github.lunatrius.schematica.network.message.MessageDownloadChunk;
import com.github.lunatrius.schematica.network.message.MessageDownloadBeginAck;
import com.github.lunatrius.schematica.network.message.MessageDownloadBegin;
import net.minecraftforge.fml.relauncher.Side;
import com.github.lunatrius.schematica.network.message.MessageCapabilities;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE;
    
    public static void init() {
        PacketHandler.INSTANCE.registerMessage((Class)MessageCapabilities.class, (Class)MessageCapabilities.class, 0, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)MessageDownloadBegin.class, (Class)MessageDownloadBegin.class, 1, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)MessageDownloadBeginAck.class, (Class)MessageDownloadBeginAck.class, 2, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)MessageDownloadChunk.class, (Class)MessageDownloadChunk.class, 3, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)MessageDownloadChunkAck.class, (Class)MessageDownloadChunkAck.class, 4, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)MessageDownloadEnd.class, (Class)MessageDownloadEnd.class, 5, Side.CLIENT);
    }
    
    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("schematica");
    }
}
