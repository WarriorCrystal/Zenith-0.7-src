//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.handler.client;

import com.github.lunatrius.schematica.client.world.SchematicWorld;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import com.github.lunatrius.schematica.client.printer.SchematicPrinter;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraft.client.Minecraft;

public class TickHandler
{
    public static final TickHandler INSTANCE;
    private final Minecraft minecraft;
    private int ticks;
    
    private TickHandler() {
        this.minecraft = Minecraft.getMinecraft();
        this.ticks = -1;
    }
    
    @SubscribeEvent
    public void onClientConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
    }
    
    @SubscribeEvent
    public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        Reference.logger.info("Scheduling client settings reset.");
        ClientProxy.isPendingReset = true;
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.minecraft.isGamePaused() || event.phase != TickEvent.Phase.END) {
            return;
        }
        this.minecraft.profiler.startSection("schematica");
        final WorldClient world = this.minecraft.world;
        final EntityPlayerSP player = this.minecraft.player;
        final SchematicWorld schematic = ClientProxy.schematic;
        if (world != null && player != null && schematic != null && schematic.isRendering) {
            this.minecraft.profiler.startSection("printer");
            final SchematicPrinter printer = SchematicPrinter.INSTANCE;
            if (printer.isEnabled() && printer.isPrinting()) {
                this.ticks = ConfigurationHandler.placeDelay;
                printer.print(world, player);
            }
            this.minecraft.profiler.endSection();
        }
        if (ClientProxy.isPendingReset) {
            Schematica.proxy.resetSettings();
            ClientProxy.isPendingReset = false;
            Reference.logger.info("Client settings have been reset.");
        }
        this.minecraft.profiler.endSection();
    }
    
    static {
        INSTANCE = new TickHandler();
    }
}
