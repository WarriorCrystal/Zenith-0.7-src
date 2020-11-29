//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketPlayer;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import me.gopro336.goprohack.events.MinecraftEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.RotationSpoof;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class AimbotModule extends Module
{
    public final Value<Modes> Mode;
    public RotationSpoof m_RotationSpoof;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public AimbotModule() {
        super("Aimbot", new String[] { "Aimbot" }, "Client aimbot for killaura, autocrystal, block placement, etc", "NONE", 5131167, ModuleType.COMBAT);
        this.Mode = new Value<Modes>("Mode", new String[] { "Modes" }, "The mode for aimbot to use", Modes.Packet);
        this.m_RotationSpoof = null;
        boolean l_IsSprinting;
        boolean l_IsSneaking;
        float l_Pitch;
        float l_Yaw;
        AxisAlignedBB axisalignedbb;
        double l_PosXDifference;
        double l_PosYDifference;
        double l_PosZDifference;
        double l_YawDifference;
        double l_RotationDifference;
        EntityPlayerSP player;
        boolean l_MovedXYZ;
        boolean l_MovedRotation;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() == MinecraftEvent.Era.PRE) {
                if (this.m_RotationSpoof != null) {
                    p_Event.cancel();
                    l_IsSprinting = this.mc.player.isSprinting();
                    if (l_IsSprinting != this.mc.player.serverSprintState) {
                        if (l_IsSprinting) {
                            this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                        }
                        else {
                            this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                        }
                        this.mc.player.serverSprintState = l_IsSprinting;
                    }
                    l_IsSneaking = this.mc.player.isSneaking();
                    if (l_IsSneaking != this.mc.player.serverSneakState) {
                        if (l_IsSneaking) {
                            this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                        }
                        else {
                            this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        }
                        this.mc.player.serverSneakState = l_IsSneaking;
                    }
                    if (PlayerUtil.isCurrentViewEntity()) {
                        l_Pitch = this.mc.player.rotationPitch;
                        l_Yaw = this.mc.player.rotationYaw;
                        if (this.m_RotationSpoof != null) {
                            l_Pitch = this.m_RotationSpoof.Pitch;
                            l_Yaw = this.m_RotationSpoof.Yaw;
                            switch (this.Mode.getValue()) {
                                case Client: {
                                    this.mc.player.rotationPitch = l_Pitch;
                                    this.mc.player.rotationYaw = l_Yaw;
                                    break;
                                }
                                default: {
                                    this.mc.player.rotationYawHead = l_Yaw;
                                    break;
                                }
                            }
                        }
                        axisalignedbb = this.mc.player.getEntityBoundingBox();
                        l_PosXDifference = this.mc.player.posX - this.mc.player.lastReportedPosX;
                        l_PosYDifference = axisalignedbb.minY - this.mc.player.lastReportedPosY;
                        l_PosZDifference = this.mc.player.posZ - this.mc.player.lastReportedPosZ;
                        l_YawDifference = l_Yaw - this.mc.player.lastReportedYaw;
                        l_RotationDifference = l_Pitch - this.mc.player.lastReportedPitch;
                        player = this.mc.player;
                        ++player.positionUpdateTicks;
                        l_MovedXYZ = (l_PosXDifference * l_PosXDifference + l_PosYDifference * l_PosYDifference + l_PosZDifference * l_PosZDifference > 9.0E-4 || this.mc.player.positionUpdateTicks >= 20);
                        l_MovedRotation = (l_YawDifference != 0.0 || l_RotationDifference != 0.0);
                        if (this.mc.player.isRiding()) {
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.mc.player.motionX, -999.0, this.mc.player.motionZ, l_Yaw, l_Pitch, this.mc.player.onGround));
                            l_MovedXYZ = false;
                        }
                        else if (l_MovedXYZ && l_MovedRotation) {
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.mc.player.posX, axisalignedbb.minY, this.mc.player.posZ, l_Yaw, l_Pitch, this.mc.player.onGround));
                        }
                        else if (l_MovedXYZ) {
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, axisalignedbb.minY, this.mc.player.posZ, this.mc.player.onGround));
                        }
                        else if (l_MovedRotation) {
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(l_Yaw, l_Pitch, this.mc.player.onGround));
                        }
                        else if (this.mc.player.prevOnGround != this.mc.player.onGround) {
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer(this.mc.player.onGround));
                        }
                        if (l_MovedXYZ) {
                            this.mc.player.lastReportedPosX = this.mc.player.posX;
                            this.mc.player.lastReportedPosY = axisalignedbb.minY;
                            this.mc.player.lastReportedPosZ = this.mc.player.posZ;
                            this.mc.player.positionUpdateTicks = 0;
                        }
                        if (l_MovedRotation) {
                            this.mc.player.lastReportedYaw = l_Yaw;
                            this.mc.player.lastReportedPitch = l_Pitch;
                        }
                        this.mc.player.prevOnGround = this.mc.player.onGround;
                        this.mc.player.autoJumpEnabled = this.mc.player.mc.gameSettings.autoJump;
                    }
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return this.Mode.getValue().toString();
    }
    
    public enum Modes
    {
        Packet, 
        Client;
    }
}
