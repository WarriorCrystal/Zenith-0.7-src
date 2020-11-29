//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;
import me.gopro336.goprohack.events.MinecraftEvent;
import java.util.function.Predicate;
import me.gopro336.goprohack.util.MathUtil;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerTravel;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class FlightModule extends Module
{
    public final Value<Modes> Mode;
    public final Value<Float> Speed;
    public final Value<Boolean> Glide;
    public final Value<Boolean> GlideWhileMoving;
    public final Value<Float> GlideSpeed;
    public final Value<Boolean> ElytraOnly;
    public final Value<Boolean> AntiFallDmg;
    public final Value<Boolean> AntiKick;
    @EventHandler
    private Listener<EventPlayerTravel> OnTravel;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public FlightModule() {
        super("Flight", new String[] { "Flight" }, "Allows you to fly", "NONE", 5131167, ModuleType.MOVEMENT);
        this.Mode = new Value<Modes>("Mode", new String[] { "M" }, "Modes of the speed to use", Modes.Vanilla);
        this.Speed = new Value<Float>("Speed", new String[] { "" }, "Speed to use", 1.0f, 0.0f, 10.0f, 1.0f);
        this.Glide = new Value<Boolean>("Glide", new String[] { "" }, "Allows the glide speed under this to function.", false);
        this.GlideWhileMoving = new Value<Boolean>("GlideWhileMoving", new String[] { "" }, "If no binds are pressed, should glide be enabled?", false);
        this.GlideSpeed = new Value<Float>("GlideSpeed", new String[] { "GlideSpeed" }, "Glide speed of going down", 0.0f, 0.0f, 10.0f, 1.0f);
        this.ElytraOnly = new Value<Boolean>("Elytra", new String[] { "" }, "Only functions while on an elytra.", false);
        this.AntiFallDmg = new Value<Boolean>("AntiFallDmg", new String[] { "" }, "Prevents you from taking fall damage while flying", false);
        this.AntiKick = new Value<Boolean>("AntiKick", new String[] { "" }, "Prevents you from getting kicked while flying by vanilla anticheat", true);
        double[] dir;
        EntityPlayerSP player;
        this.OnTravel = new Listener<EventPlayerTravel>(p_Event -> {
            if (this.mc.player == null) {
                return;
            }
            else if (this.ElytraOnly.getValue() && !this.mc.player.isElytraFlying()) {
                return;
            }
            else {
                if (this.Mode.getValue() == Modes.Creative) {
                    this.mc.player.setVelocity(0.0, 0.0, 0.0);
                    dir = MathUtil.directionSpeed(this.Speed.getValue());
                    if (this.mc.player.movementInput.moveStrafe != 0.0f || this.mc.player.movementInput.moveForward != 0.0f) {
                        this.mc.player.motionX = dir[0];
                        this.mc.player.motionZ = dir[1];
                    }
                    if (this.mc.player.movementInput.jump && !this.mc.player.isElytraFlying()) {
                        this.mc.player.motionY = this.Speed.getValue();
                    }
                    if (this.mc.player.movementInput.sneak) {
                        this.mc.player.motionY = -this.Speed.getValue();
                    }
                    if (this.Glide.getValue() && (!this.GlideWhileMoving.getValue() || this.mc.player.movementInput.moveStrafe != 0.0f || this.mc.player.movementInput.moveForward != 0.0f)) {
                        player = this.mc.player;
                        player.motionY += -this.GlideSpeed.getValue();
                    }
                    p_Event.cancel();
                    this.mc.player.prevLimbSwingAmount = 0.0f;
                    this.mc.player.limbSwingAmount = 0.0f;
                    this.mc.player.limbSwing = 0.0f;
                }
                return;
            }
        }, (Predicate<EventPlayerTravel>[])new Predicate[0]);
        double[] dir2;
        EntityPlayerSP player2;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() != MinecraftEvent.Era.PRE) {
                return;
            }
            else if (this.ElytraOnly.getValue() && !this.mc.player.isElytraFlying()) {
                return;
            }
            else {
                if (this.Mode.getValue() == Modes.Vanilla) {
                    this.mc.player.setVelocity(0.0, 0.0, 0.0);
                    dir2 = MathUtil.directionSpeed(this.Speed.getValue());
                    if (this.mc.player.movementInput.moveStrafe != 0.0f || this.mc.player.movementInput.moveForward != 0.0f) {
                        this.mc.player.motionX = dir2[0];
                        this.mc.player.motionZ = dir2[1];
                    }
                    if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                        this.mc.player.motionY = this.Speed.getValue();
                    }
                    if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                        this.mc.player.motionY = -this.Speed.getValue();
                    }
                }
                if (this.AntiKick.getValue() && this.mc.player.ticksExisted % 4 == 0) {
                    player2 = this.mc.player;
                    player2.motionY -= 0.04;
                }
                return;
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
        CPacketPlayer l_Packet;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof CPacketPlayer) {
                if (!(!this.AntiFallDmg.getValue())) {
                    if (!this.mc.player.isElytraFlying()) {
                        l_Packet = (CPacketPlayer)p_Event.getPacket();
                        if (this.mc.player.fallDistance > 3.8f) {
                            l_Packet.onGround = true;
                            this.mc.player.fallDistance = 0.0f;
                        }
                    }
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
    
    public enum Modes
    {
        Vanilla, 
        Creative;
    }
}
