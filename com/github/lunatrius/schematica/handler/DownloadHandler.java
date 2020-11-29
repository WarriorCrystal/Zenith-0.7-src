//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.handler;

import com.github.lunatrius.schematica.network.message.MessageDownloadEnd;
import com.github.lunatrius.schematica.network.message.MessageDownloadChunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import com.github.lunatrius.schematica.network.PacketHandler;
import com.github.lunatrius.schematica.network.message.MessageDownloadBegin;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.LinkedHashMap;
import com.github.lunatrius.schematica.network.transfer.SchematicTransfer;
import net.minecraft.entity.player.EntityPlayerMP;
import java.util.Map;
import com.github.lunatrius.schematica.api.ISchematic;

public class DownloadHandler
{
    public static final DownloadHandler INSTANCE;
    public ISchematic schematic;
    public final Map<EntityPlayerMP, SchematicTransfer> transferMap;
    
    private DownloadHandler() {
        this.schematic = null;
        this.transferMap = new LinkedHashMap<EntityPlayerMP, SchematicTransfer>();
    }
    
    @SubscribeEvent
    public void onServerTick(final TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        this.processQueue();
    }
    
    private void processQueue() {
        if (this.transferMap.size() == 0) {
            return;
        }
        final EntityPlayerMP player = this.transferMap.keySet().iterator().next();
        final SchematicTransfer transfer = this.transferMap.remove(player);
        if (transfer == null) {
            return;
        }
        if (!transfer.state.isWaiting()) {
            if (++transfer.timeout >= 300) {
                if (++transfer.retries >= 5) {
                    Reference.logger.warn("{}'s download was dropped!", (Object)player.getName());
                    return;
                }
                Reference.logger.warn("{}'s download timed out, retrying (#{})", (Object)player.getName(), (Object)transfer.retries);
                this.sendChunk(player, transfer);
                transfer.timeout = 0;
            }
        }
        else if (transfer.state == SchematicTransfer.State.BEGIN_WAIT) {
            this.sendBegin(player, transfer);
        }
        else if (transfer.state == SchematicTransfer.State.CHUNK_WAIT) {
            this.sendChunk(player, transfer);
        }
        else if (transfer.state == SchematicTransfer.State.END_WAIT) {
            this.sendEnd(player, transfer);
            return;
        }
        this.transferMap.put(player, transfer);
    }
    
    private void sendBegin(final EntityPlayerMP player, final SchematicTransfer transfer) {
        transfer.setState(SchematicTransfer.State.BEGIN);
        final MessageDownloadBegin message = new MessageDownloadBegin(transfer.schematic);
        PacketHandler.INSTANCE.sendTo((IMessage)message, player);
    }
    
    private void sendChunk(final EntityPlayerMP player, final SchematicTransfer transfer) {
        transfer.setState(SchematicTransfer.State.CHUNK);
        Reference.logger.trace("Sending chunk {},{},{}", (Object)transfer.baseX, (Object)transfer.baseY, (Object)transfer.baseZ);
        final MessageDownloadChunk message = new MessageDownloadChunk(transfer.schematic, transfer.baseX, transfer.baseY, transfer.baseZ);
        PacketHandler.INSTANCE.sendTo((IMessage)message, player);
    }
    
    private void sendEnd(final EntityPlayerMP player, final SchematicTransfer transfer) {
        final MessageDownloadEnd message = new MessageDownloadEnd(transfer.name);
        PacketHandler.INSTANCE.sendTo((IMessage)message, player);
    }
    
    static {
        INSTANCE = new DownloadHandler();
    }
}
