//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.schematica;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.network.play.client.CPacketPlayer;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import me.gopro336.goprohack.events.MinecraftEvent;
import java.util.function.Predicate;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import net.minecraft.util.math.Vec3d;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.gopro336.goprohack.events.schematica.EventSchematicaPlaceBlockFull;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.schematica.EventSchematicaPlaceBlock;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class PrinterBypassModule extends Module
{
    public final Value<Modes> Mode;
    public final Value<Float> Delay;
    private BlockPos BlockToPlace;
    private Item NeededItem;
    private Timer timer;
    @EventHandler
    private Listener<EventSchematicaPlaceBlock> Position;
    @EventHandler
    private Listener<EventSchematicaPlaceBlockFull> OnSchematicaPlaceBlockFull;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public PrinterBypassModule() {
        super("PrinterBypass", new String[] { "PrinterNCP" }, "Faces block rotations on schematica place block events", "NONE", 5131167, ModuleType.WORLD);
        this.Mode = new Value<Modes>("Mode", new String[] { "Mode" }, "Which mode to use for printer bypass", Modes.Full);
        this.Delay = new Value<Float>("Delay", new String[] { "Delay" }, "Delay of the place for Full mode", 0.0f, 0.0f, 1.0f, 0.1f);
        this.BlockToPlace = null;
        this.NeededItem = null;
        this.timer = new Timer();
        this.Position = new Listener<EventSchematicaPlaceBlock>(p_Event -> {
            if (this.Mode.getValue() == Modes.Packet) {
                BlockInteractionHelper.faceVectorPacketInstant(new Vec3d((double)p_Event.Pos.getX(), (double)p_Event.Pos.getY(), (double)p_Event.Pos.getZ()));
            }
            return;
        }, (Predicate<EventSchematicaPlaceBlock>[])new Predicate[0]);
        boolean l_Result;
        this.OnSchematicaPlaceBlockFull = new Listener<EventSchematicaPlaceBlockFull>(p_Event -> {
            if (this.Mode.getValue() != Modes.Full) {
                return;
            }
            else {
                p_Event.cancel();
                l_Result = (this.BlockToPlace == null);
                if (l_Result) {
                    this.BlockToPlace = p_Event.Pos;
                }
                p_Event.Result = l_Result;
                if (p_Event.ItemStack != null) {
                    this.NeededItem = p_Event.ItemStack;
                }
                else {
                    this.NeededItem = null;
                }
                return;
            }
        }, (Predicate<EventSchematicaPlaceBlockFull>[])new Predicate[0]);
        float[] rotations;
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
        BlockInteractionHelper.PlaceResult l_Place;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() == MinecraftEvent.Era.PRE) {
                if (this.BlockToPlace != null) {
                    if (!(!this.timer.passed(this.Delay.getValue() * 1000.0f))) {
                        this.timer.reset();
                        rotations = BlockInteractionHelper.getLegitRotations(new Vec3d((double)this.BlockToPlace.getX(), (double)this.BlockToPlace.getY(), (double)this.BlockToPlace.getZ()));
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
                            l_Pitch = rotations[1];
                            l_Yaw = rotations[0];
                            this.mc.player.rotationYawHead = l_Yaw;
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
                        l_Place = BlockInteractionHelper.place(this.BlockToPlace, 5.0f, false, false);
                        this.BlockToPlace = null;
                    }
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.BlockToPlace = null;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.BlockToPlace = null;
    }
    
    public enum Modes
    {
        Packet, 
        Full;
    }
}
