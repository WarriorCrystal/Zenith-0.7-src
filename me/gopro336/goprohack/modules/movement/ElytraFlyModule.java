//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import me.gopro336.goprohack.util.MathUtil;
import me.gopro336.goprohack.main.GoproHack;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemElytra;
import me.gopro336.goprohack.managers.ModuleManager;
import net.minecraft.network.play.client.CPacketPlayer;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerTravel;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class ElytraFlyModule extends Module
{
    public final Value<Mode> mode;
    public final Value<Float> speed;
    public final Value<Float> DownSpeed;
    public final Value<Float> GlideSpeed;
    public final Value<Float> UpSpeed;
    public final Value<Boolean> Accelerate;
    public final Value<Integer> vAccelerationTimer;
    public final Value<Float> RotationPitch;
    public final Value<Boolean> CancelInWater;
    public final Value<Integer> CancelAtHeight;
    public final Value<Boolean> InstantFly;
    public final Value<Boolean> EquipElytra;
    public final Value<Boolean> PitchSpoof;
    private Timer PacketTimer;
    private Timer AccelerationTimer;
    private Timer AccelerationResetTimer;
    private Timer InstantFlyTimer;
    private boolean SendMessage;
    private FlightModule Flight;
    private int ElytraSlot;
    @EventHandler
    private Listener<EventPlayerTravel> OnTravel;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public ElytraFlyModule() {
        super("ElytraFly", new String[] { "Elytra+" }, "Assists Flying With Elytra", "NONE", 5131167, ModuleType.MOVEMENT);
        this.mode = new Value<Mode>("Mode", new String[] { "Mode", "M" }, "Mode to use for elytra flight.", Mode.Normal);
        this.speed = new Value<Float>("Speed", new String[] { "Spd" }, "Speed multiplier - 2b speed recommended is 1.8~", 1.82f, 0.0f, 10.0f, 0.1f);
        this.DownSpeed = new Value<Float>("DownSpeed", new String[] { "DS" }, "DownSpeed multiplier for flight.", 1.82f, 0.0f, 10.0f, 0.1f);
        this.GlideSpeed = new Value<Float>("GlideSpeed", new String[] { "GlideSpeed" }, "Glide value for acceleration, this is divided by 10000.", 1.0f, 0.0f, 10.0f, 1.0f);
        this.UpSpeed = new Value<Float>("UpSpeed", new String[] { "UpSpeed" }, "Up speed for elytra.", 2.0f, 0.0f, 20.0f, 1.0f);
        this.Accelerate = new Value<Boolean>("Accelerate", new String[] { "Accelerate", "Accelerate" }, "Auto accelerates when going up", true);
        this.vAccelerationTimer = new Value<Integer>("Timer", new String[] { "AT" }, "Acceleration timer, default 1000", 1000, 0, 10000, 1000);
        this.RotationPitch = new Value<Float>("RotationPitch", new String[] { "RP" }, "RotationPitch default 0.0, this is for going up, -90 is lowest you can face, 90 is highest", 0.0f, -90.0f, 90.0f, 10.0f);
        this.CancelInWater = new Value<Boolean>("CancelInWater", new String[] { "CiW" }, "Cancel in water, anticheat will flag you if you try to go up in water, accelerating will still work.", true);
        this.CancelAtHeight = new Value<Integer>("CancelAtHeight", new String[] { "CAH" }, "Doesn't allow flight Y is below, or if too close to bedrock. since 2b anticheat is gay", 5, 0, 10, 1);
        this.InstantFly = new Value<Boolean>("InstantFly", new String[] { "IF" }, "Sends the fall flying packet when your off ground", true);
        this.EquipElytra = new Value<Boolean>("EquipElytra", new String[] { "EE" }, "Equips your elytra when enabled if you're not already wearing one", false);
        this.PitchSpoof = new Value<Boolean>("PitchSpoof", new String[] { "PS" }, "Spoofs your pitch for hauses new patch", false);
        this.PacketTimer = new Timer();
        this.AccelerationTimer = new Timer();
        this.AccelerationResetTimer = new Timer();
        this.InstantFlyTimer = new Timer();
        this.SendMessage = false;
        this.Flight = null;
        this.ElytraSlot = -1;
        this.OnTravel = new Listener<EventPlayerTravel>(p_Event -> {
            if (this.mc.player == null || this.Flight.isEnabled()) {
                return;
            }
            else if (this.mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.ELYTRA) {
                return;
            }
            else if (!this.mc.player.isElytraFlying()) {
                if (!this.mc.player.onGround && this.InstantFly.getValue()) {
                    if (!(!this.InstantFlyTimer.passed(1000.0))) {
                        this.InstantFlyTimer.reset();
                        this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                    }
                }
                return;
            }
            else {
                switch (this.mode.getValue()) {
                    case Pro: {
                        this.HandleImmediateModeElytra(p_Event);
                    }
                    case Normal:
                    case Jetpack:
                    case Tarzan:
                    case Creative: {
                        this.HandleControlMode(p_Event);
                    }
                    case Packet: {
                        this.HandleNormalModeElytra(p_Event);
                        break;
                    }
                    case Superior: {
                        this.HandleImmediateModeElytra(p_Event);
                        break;
                    }
                    case Control: {
                        this.HandleControlMode(p_Event);
                        break;
                    }
                }
                return;
            }
        }, (Predicate<EventPlayerTravel>[])new Predicate[0]);
        CPacketPlayer.PositionRotation rotation;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof CPacketPlayer && this.PitchSpoof.getValue()) {
                if (!(!this.mc.player.isElytraFlying())) {
                    if (p_Event.getPacket() instanceof CPacketPlayer.PositionRotation && this.PitchSpoof.getValue()) {
                        rotation = (CPacketPlayer.PositionRotation)p_Event.getPacket();
                        this.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(rotation.x, rotation.y, rotation.z, rotation.onGround));
                        p_Event.cancel();
                    }
                    else if (p_Event.getPacket() instanceof CPacketPlayer.Rotation && this.PitchSpoof.getValue()) {
                        p_Event.cancel();
                    }
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.Flight = (FlightModule)ModuleManager.Get().GetMod(FlightModule.class);
        this.ElytraSlot = -1;
        if (this.EquipElytra.getValue() && this.mc.player != null && this.mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.ELYTRA) {
            for (int l_I = 0; l_I < 44; ++l_I) {
                final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
                if (!l_Stack.isEmpty() && l_Stack.getItem() == Items.ELYTRA) {
                    final ItemElytra l_Elytra = (ItemElytra)l_Stack.getItem();
                    this.ElytraSlot = l_I;
                    break;
                }
            }
            if (this.ElytraSlot != -1) {
                final boolean l_HasArmorAtChest = this.mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.AIR;
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, this.ElytraSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                if (l_HasArmorAtChest) {
                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, this.ElytraSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                }
            }
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mc.player == null) {
            return;
        }
        if (this.ElytraSlot != -1) {
            final boolean l_HasItem = !this.mc.player.inventory.getStackInSlot(this.ElytraSlot).isEmpty() || this.mc.player.inventory.getStackInSlot(this.ElytraSlot).getItem() != Items.AIR;
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, this.ElytraSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            if (l_HasItem) {
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            }
        }
    }
    
    @Override
    public String getMetaData() {
        return this.mode.getValue().name();
    }
    
    public void HandleNormalModeElytra(final EventPlayerTravel p_Travel) {
        final double l_YHeight = this.mc.player.posY;
        if (l_YHeight <= this.CancelAtHeight.getValue()) {
            if (!this.SendMessage) {
                GoproHack.SendMessage(ChatFormatting.RED + "WARNING, you must scaffold up or use fireworks, as YHeight <= CancelAtHeight!");
                this.SendMessage = true;
            }
            return;
        }
        final boolean l_IsMoveKeyDown = this.mc.player.movementInput.moveForward > 0.0f || this.mc.player.movementInput.moveStrafe > 0.0f;
        final boolean l_CancelInWater = !this.mc.player.isInWater() && !this.mc.player.isInLava() && this.CancelInWater.getValue();
        if (this.mc.player.movementInput.jump) {
            p_Travel.cancel();
            this.Accelerate();
            return;
        }
        if (!l_IsMoveKeyDown) {
            this.AccelerationTimer.resetTimeSkipTo(-this.vAccelerationTimer.getValue());
        }
        else if ((this.mc.player.rotationPitch <= this.RotationPitch.getValue() || this.mode.getValue() == Mode.Tarzan) && l_CancelInWater) {
            if (this.Accelerate.getValue() && this.AccelerationTimer.passed(this.vAccelerationTimer.getValue())) {
                this.Accelerate();
            }
            return;
        }
        p_Travel.cancel();
        this.Accelerate();
    }
    
    public void HandleImmediateModeElytra(final EventPlayerTravel p_Travel) {
        if (!this.mc.player.movementInput.jump) {
            this.mc.player.setVelocity(0.0, 0.0, 0.0);
            p_Travel.cancel();
            final double[] dir = MathUtil.directionSpeed(this.speed.getValue());
            if (this.mc.player.movementInput.moveStrafe != 0.0f || this.mc.player.movementInput.moveForward != 0.0f) {
                this.mc.player.motionX = dir[0];
                this.mc.player.motionY = -(this.GlideSpeed.getValue() / 10000.0f);
                this.mc.player.motionZ = dir[1];
            }
            if (this.mc.player.movementInput.sneak) {
                this.mc.player.motionY = -this.DownSpeed.getValue();
            }
            this.mc.player.prevLimbSwingAmount = 0.0f;
            this.mc.player.limbSwingAmount = 0.0f;
            this.mc.player.limbSwing = 0.0f;
            return;
        }
        final double l_MotionSq = Math.sqrt(this.mc.player.motionX * this.mc.player.motionX + this.mc.player.motionZ * this.mc.player.motionZ);
        if (l_MotionSq > 1.0) {
            return;
        }
        final double[] dir2 = MathUtil.directionSpeedNoForward(this.speed.getValue());
        this.mc.player.motionX = dir2[0];
        this.mc.player.motionY = -(this.GlideSpeed.getValue() / 10000.0f);
        this.mc.player.motionZ = dir2[1];
        p_Travel.cancel();
    }
    
    public void Accelerate() {
        if (this.AccelerationResetTimer.passed(this.vAccelerationTimer.getValue())) {
            this.AccelerationResetTimer.reset();
            this.AccelerationTimer.reset();
            this.SendMessage = false;
        }
        final float l_Speed = this.speed.getValue();
        final double[] dir = MathUtil.directionSpeed(l_Speed);
        this.mc.player.motionY = -(this.GlideSpeed.getValue() / 10000.0f);
        if (this.mc.player.movementInput.moveStrafe != 0.0f || this.mc.player.movementInput.moveForward != 0.0f) {
            this.mc.player.motionX = dir[0];
            this.mc.player.motionZ = dir[1];
        }
        else {
            this.mc.player.motionX = 0.0;
            this.mc.player.motionZ = 0.0;
        }
        if (this.mc.player.movementInput.sneak) {
            this.mc.player.motionY = -this.DownSpeed.getValue();
        }
        this.mc.player.prevLimbSwingAmount = 0.0f;
        this.mc.player.limbSwingAmount = 0.0f;
        this.mc.player.limbSwing = 0.0f;
    }
    
    private void HandleControlMode(final EventPlayerTravel p_Event) {
        final double[] dir = MathUtil.directionSpeed(this.speed.getValue());
        if (this.mc.player.movementInput.moveStrafe != 0.0f || this.mc.player.movementInput.moveForward != 0.0f) {
            this.mc.player.motionX = dir[0];
            this.mc.player.motionZ = dir[1];
            final EntityPlayerSP player = this.mc.player;
            player.motionX -= this.mc.player.motionX * (Math.abs(this.mc.player.rotationPitch) + 90.0f) / 90.0 - this.mc.player.motionX;
            final EntityPlayerSP player2 = this.mc.player;
            player2.motionZ -= this.mc.player.motionZ * (Math.abs(this.mc.player.rotationPitch) + 90.0f) / 90.0 - this.mc.player.motionZ;
        }
        else {
            this.mc.player.motionX = 0.0;
            this.mc.player.motionZ = 0.0;
        }
        this.mc.player.motionY = -MathUtil.degToRad(this.mc.player.rotationPitch) * this.mc.player.movementInput.moveForward;
        this.mc.player.prevLimbSwingAmount = 0.0f;
        this.mc.player.limbSwingAmount = 0.0f;
        this.mc.player.limbSwing = 0.0f;
        p_Event.cancel();
    }
    
    private enum Mode
    {
        Pro, 
        Normal, 
        Jetpack, 
        Tarzan, 
        Superior, 
        Creative, 
        Packet, 
        Control;
    }
}
