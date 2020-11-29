// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.handler;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import com.github.lunatrius.schematica.network.message.MessageCapabilities;
import com.github.lunatrius.schematica.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerHandler
{
    public static final PlayerHandler INSTANCE;
    
    private PlayerHandler() {
    }
    
    @SubscribeEvent
    public void onPlayerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            try {
                PacketHandler.INSTANCE.sendTo((IMessage)new MessageCapabilities(ConfigurationHandler.printerEnabled, ConfigurationHandler.saveEnabled, ConfigurationHandler.loadEnabled), (EntityPlayerMP)event.player);
            }
            catch (Exception ex) {
                Reference.logger.error("Failed to send capabilities!", (Throwable)ex);
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerLoggedOut(final PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            DownloadHandler.INSTANCE.transferMap.remove(event.player);
        }
    }
    
    static {
        INSTANCE = new PlayerHandler();
    }
}
