//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.handler.client;

import com.github.lunatrius.schematica.reference.Reference;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.World;
import net.minecraft.world.IWorldEventListener;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.github.lunatrius.schematica.client.renderer.RenderSchematic;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import net.minecraftforge.event.world.WorldEvent;

public class WorldHandler
{
    @SubscribeEvent
    public void onLoad(final WorldEvent.Load event) {
        final World world = event.getWorld();
        if (world.isRemote && !(world instanceof SchematicWorld)) {
            RenderSchematic.INSTANCE.setWorldAndLoadRenderers(ClientProxy.schematic);
            addWorldAccess(world, (IWorldEventListener)RenderSchematic.INSTANCE);
        }
    }
    
    @SubscribeEvent
    public void onUnload(final WorldEvent.Unload event) {
        final World world = event.getWorld();
        if (world.isRemote) {
            removeWorldAccess(world, (IWorldEventListener)RenderSchematic.INSTANCE);
        }
    }
    
    public static void addWorldAccess(final World world, final IWorldEventListener schematic) {
        if (world != null && schematic != null) {
            Reference.logger.debug("Adding world access to {}", (Object)world);
            world.addEventListener(schematic);
        }
    }
    
    public static void removeWorldAccess(final World world, final IWorldEventListener schematic) {
        if (world != null && schematic != null) {
            Reference.logger.debug("Removing world access from {}", (Object)world);
            world.removeEventListener(schematic);
        }
    }
}
