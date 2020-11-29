//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.handler;

import com.github.lunatrius.schematica.world.schematic.SchematicFormat;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.ArrayDeque;
import com.github.lunatrius.schematica.world.chunk.SchematicContainer;
import java.util.Queue;

public class QueueTickHandler
{
    public static final QueueTickHandler INSTANCE;
    private final Queue<SchematicContainer> queue;
    
    private QueueTickHandler() {
        this.queue = new ArrayDeque<SchematicContainer>();
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        try {
            final EntityPlayerSP player = Minecraft.getMinecraft().player;
            if (player != null && player.connection != null && !player.connection.getNetworkManager().isLocalChannel()) {
                this.processQueue();
            }
        }
        catch (Exception e) {
            Reference.logger.error("Something went wrong...", (Throwable)e);
        }
    }
    
    @SubscribeEvent
    public void onServerTick(final TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        this.processQueue();
    }
    
    private void processQueue() {
        if (this.queue.size() == 0) {
            return;
        }
        final SchematicContainer container = this.queue.poll();
        if (container == null) {
            return;
        }
        if (container.hasNext()) {
            if (container.isFirst()) {
                final TextComponentTranslation component = new TextComponentTranslation("schematica.command.save.started", new Object[] { container.chunkCount, container.file.getName() });
                container.player.sendMessage((ITextComponent)component);
            }
            container.next();
        }
        if (container.hasNext()) {
            this.queue.offer(container);
        }
        else {
            SchematicFormat.writeToFileAndNotify(container.file, container.format, container.schematic, container.player);
        }
    }
    
    public void queueSchematic(final SchematicContainer container) {
        this.queue.offer(container);
    }
    
    static {
        INSTANCE = new QueueTickHandler();
    }
}
