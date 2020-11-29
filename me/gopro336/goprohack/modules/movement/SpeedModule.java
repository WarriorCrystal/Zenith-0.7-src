//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.MobEffects;
import me.gopro336.goprohack.managers.ModuleManager;
import me.gopro336.goprohack.events.MinecraftEvent;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import me.gopro336.goprohack.events.player.EventPlayerMove;
import me.gopro336.goprohack.events.player.EventPlayerJump;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.world.TimerModule;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class SpeedModule extends Module
{
    public final Value<Modes> Mode;
    public final Value<Boolean> UseTimer;
    public final Value<Boolean> AutoSprint;
    public final Value<Boolean> SpeedInWater;
    public final Value<Boolean> AutoJump;
    public final Value<Boolean> Strict;
    private TimerModule Timer;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerJump> OnPlayerJump;
    @EventHandler
    private Listener<EventPlayerMove> OnPlayerMove;
    
    public SpeedModule() {
        super("Speed", new String[] { "Strafe" }, "Speed strafe", "NONE", 5131167, ModuleType.MOVEMENT);
        this.Mode = new Value<Modes>("Mode", new String[] { "Mode" }, "The mode of speed to use", Modes.Strafe);
        this.UseTimer = new Value<Boolean>("UseTimer", new String[] { "UseTimer" }, "Uses timer to go faster", false);
        this.AutoSprint = new Value<Boolean>("AutoSprint", new String[] { "AutoSprint" }, "Automatically sprints for you", false);
        this.SpeedInWater = new Value<Boolean>("SpeedInWater", new String[] { "SpeedInWater" }, "Speeds in water", false);
        this.AutoJump = new Value<Boolean>("AutoJump", new String[] { "AutoJump" }, "Automatically jumps", true);
        this.Strict = new Value<Boolean>("Strict", new String[] { "Strict" }, "Strict mode, use this for when hauses patch comes back for strafe", false);
        this.Timer = null;
        float yaw;
        EntityPlayerSP player;
        EntityPlayerSP player2;
        float yaw2;
        EntityPlayerSP player3;
        EntityPlayerSP player4;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.mc.player.isRiding()) {
                return;
            }
            else if ((this.mc.player.isInWater() || this.mc.player.isInLava()) && !this.SpeedInWater.getValue()) {
                return;
            }
            else {
                if (this.UseTimer.getValue()) {
                    this.Timer.SetOverrideSpeed(1.088f);
                }
                if (this.mc.player.moveForward != 0.0f || this.mc.player.moveStrafing != 0.0f) {
                    if (this.AutoSprint.getValue()) {
                        this.mc.player.setSprinting(true);
                    }
                    if (this.mc.player.onGround && this.Mode.getValue() == Modes.Strafe) {
                        if (this.AutoJump.getValue()) {
                            this.mc.player.motionY = 0.4050000011920929;
                        }
                        yaw = this.GetRotationYawForCalc();
                        player = this.mc.player;
                        player.motionX -= MathHelper.sin(yaw) * 0.2f;
                        player2 = this.mc.player;
                        player2.motionZ += MathHelper.cos(yaw) * 0.2f;
                    }
                    else if (this.mc.player.onGround && this.Mode.getValue() == Modes.OnGround) {
                        yaw2 = this.GetRotationYawForCalc();
                        player3 = this.mc.player;
                        player3.motionX -= MathHelper.sin(yaw2) * 0.2f;
                        player4 = this.mc.player;
                        player4.motionZ += MathHelper.cos(yaw2) * 0.2f;
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.4, this.mc.player.posZ, false));
                    }
                }
                if (this.mc.gameSettings.keyBindJump.isKeyDown() && this.mc.player.onGround) {
                    this.mc.player.motionY = 0.4050000011920929;
                }
                return;
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        this.OnPlayerJump = new Listener<EventPlayerJump>(p_Event -> {
            if (this.Mode.getValue() == Modes.Strafe) {
                p_Event.cancel();
            }
            return;
        }, (Predicate<EventPlayerJump>[])new Predicate[0]);
        float playerSpeed;
        float moveForward;
        float moveStrafe;
        float rotationYaw;
        int amplifier;
        this.OnPlayerMove = new Listener<EventPlayerMove>(p_Event -> {
            if (p_Event.getEra() == MinecraftEvent.Era.PRE && this.Mode.getValue() != Modes.OnGround) {
                if ((!this.mc.player.isInWater() && !this.mc.player.isInLava()) || this.SpeedInWater.getValue()) {
                    if (this.mc.player.capabilities == null || (!this.mc.player.capabilities.isFlying && !ModuleManager.Get().GetMod(FlightModule.class).isEnabled() && !this.mc.player.isElytraFlying())) {
                        if (!this.mc.player.onGround) {
                            playerSpeed = 0.2873f;
                            moveForward = this.mc.player.movementInput.moveForward;
                            moveStrafe = this.mc.player.movementInput.moveStrafe;
                            rotationYaw = this.mc.player.rotationYaw;
                            if (this.mc.player.isPotionActive(MobEffects.SPEED)) {
                                amplifier = this.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                                playerSpeed *= 1.0f + 0.2f * (amplifier + 1);
                            }
                            if (!this.Strict.getValue()) {
                                playerSpeed *= 1.0064f;
                            }
                            if (moveForward == 0.0f && moveStrafe == 0.0f) {
                                p_Event.X = 0.0;
                                p_Event.Z = 0.0;
                            }
                            else {
                                if (moveForward != 0.0f) {
                                    if (moveStrafe > 0.0f) {
                                        rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
                                    }
                                    else if (moveStrafe < 0.0f) {
                                        rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
                                    }
                                    moveStrafe = 0.0f;
                                    if (moveForward > 0.0f) {
                                        moveForward = 1.0f;
                                    }
                                    else if (moveForward < 0.0f) {
                                        moveForward = -1.0f;
                                    }
                                }
                                p_Event.X = moveForward * playerSpeed * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + moveStrafe * playerSpeed * Math.sin(Math.toRadians(rotationYaw + 90.0f));
                                p_Event.Z = moveForward * playerSpeed * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - moveStrafe * playerSpeed * Math.cos(Math.toRadians(rotationYaw + 90.0f));
                            }
                            p_Event.cancel();
                        }
                    }
                }
            }
        }, (Predicate<EventPlayerMove>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.Timer = (TimerModule)ModuleManager.Get().GetMod(TimerModule.class);
    }
    
    private float GetRotationYawForCalc() {
        float rotationYaw = this.mc.player.rotationYaw;
        if (this.mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n = 1.0f;
        if (this.mc.player.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (this.mc.player.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (this.mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (this.mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.UseTimer.getValue()) {
            this.Timer.SetOverrideSpeed(1.0f);
        }
    }
    
    public enum Modes
    {
        Strafe, 
        OnGround;
    }
}
