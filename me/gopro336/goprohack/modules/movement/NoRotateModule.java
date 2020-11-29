//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import net.minecraft.entity.player.EntityPlayer;
import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Module;

public final class NoRotateModule extends Module
{
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public NoRotateModule() {
        super("NoRotate", new String[] { "NoRot", "AntiRotate" }, "Prevents you from processing server rotations", "NONE", 5131167, ModuleType.MOVEMENT);
        EntityPlayer entityplayer;
        SPacketPlayerPosLook packetIn;
        double d0;
        double d2;
        double d3;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (this.mc.world != null && this.mc.player != null) {
                if (p_Event.getPacket() instanceof SPacketPlayerPosLook && this.mc.player != null && this.mc.getConnection().doneLoadingTerrain) {
                    p_Event.cancel();
                    entityplayer = (EntityPlayer)this.mc.player;
                    packetIn = (SPacketPlayerPosLook)p_Event.getPacket();
                    d0 = packetIn.getX();
                    d2 = packetIn.getY();
                    d3 = packetIn.getZ();
                    if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X)) {
                        d0 += entityplayer.posX;
                    }
                    else {
                        entityplayer.motionX = 0.0;
                    }
                    if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y)) {
                        d2 += entityplayer.posY;
                    }
                    else {
                        entityplayer.motionY = 0.0;
                    }
                    if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Z)) {
                        d3 += entityplayer.posZ;
                    }
                    else {
                        entityplayer.motionZ = 0.0;
                    }
                    entityplayer.setPosition(d0, d2, d3);
                    this.mc.getConnection().sendPacket((Packet)new CPacketConfirmTeleport(packetIn.getTeleportId()));
                    this.mc.getConnection().sendPacket((Packet)new CPacketPlayer.PositionRotation(entityplayer.posX, entityplayer.getEntityBoundingBox().minY, entityplayer.posZ, packetIn.yaw, packetIn.pitch, false));
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return "Packet";
    }
}
