//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.handler.client;

import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;

public class RenderTickHandler
{
    public static final RenderTickHandler INSTANCE;
    private final Minecraft minecraft;
    
    private RenderTickHandler() {
        this.minecraft = Minecraft.getMinecraft();
    }
    
    @SubscribeEvent
    public void onRenderTick(final TickEvent.RenderTickEvent event) {
        final SchematicWorld schematic = ClientProxy.schematic;
        ClientProxy.objectMouseOver = ((schematic != null) ? this.rayTrace(schematic, 1.0f) : null);
    }
    
    private RayTraceResult rayTrace(final SchematicWorld schematic, final float partialTicks) {
        final Entity renderViewEntity = this.minecraft.getRenderViewEntity();
        if (renderViewEntity == null) {
            return null;
        }
        final double blockReachDistance = this.minecraft.playerController.getBlockReachDistance();
        final double posX = renderViewEntity.posX;
        final double posY = renderViewEntity.posY;
        final double posZ = renderViewEntity.posZ;
        final Entity entity = renderViewEntity;
        entity.posX -= schematic.position.x;
        final Entity entity2 = renderViewEntity;
        entity2.posY -= schematic.position.y;
        final Entity entity3 = renderViewEntity;
        entity3.posZ -= schematic.position.z;
        final Vec3d vecPosition = renderViewEntity.getPositionEyes(partialTicks);
        final Vec3d vecLook = renderViewEntity.getLook(partialTicks);
        final Vec3d vecExtendedLook = vecPosition.add(vecLook.x * blockReachDistance, vecLook.y * blockReachDistance, vecLook.z * blockReachDistance);
        renderViewEntity.posX = posX;
        renderViewEntity.posY = posY;
        renderViewEntity.posZ = posZ;
        return schematic.rayTraceBlocks(vecPosition, vecExtendedLook, false, false, true);
    }
    
    static {
        INSTANCE = new RenderTickHandler();
    }
}
