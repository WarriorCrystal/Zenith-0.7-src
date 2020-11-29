//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketEntityStatus;
import java.util.function.Predicate;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.gopro336.goprohack.events.player.EventPlayerApplyCollision;
import me.gopro336.goprohack.events.player.EventPlayerPushedByWater;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerPushOutOfBlocks;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class VelocityModule extends Module
{
    public final Value<Integer> horizontal_vel;
    public final Value<Integer> vertical_vel;
    public final Value<Boolean> explosions;
    public final Value<Boolean> bobbers;
    public final Value<Boolean> NoPush;
    @EventHandler
    private Listener<EventPlayerPushOutOfBlocks> PushOutOfBlocks;
    @EventHandler
    private Listener<EventPlayerPushedByWater> PushByWater;
    @EventHandler
    private Listener<EventPlayerApplyCollision> ApplyCollision;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public VelocityModule() {
        super("Velocity", new String[] { "Vel", "AntiVelocity", "Knockback", "AntiKnockback" }, "Modify the velocity you take", "NONE", 5131167, ModuleType.COMBAT);
        this.horizontal_vel = new Value<Integer>("Horizontal", new String[] { "Horizontal_Velocity", "HVel", "HV", "HorizontalVel", "Horizontal", "H" }, "The horizontal velocity you will take.", 0, 0, 100, 1);
        this.vertical_vel = new Value<Integer>("Veritcal", new String[] { "Vertical_Velocity", "VVel", "VV", "VerticalVel", "Vertical", "Vert", "V" }, "The vertical velocity you will take.", 0, 0, 100, 1);
        this.explosions = new Value<Boolean>("Explosions", new String[] { "Explosions", "Explosion", "EXP", "EX", "Expl" }, "Apply velocity modifier on explosion velocity.", true);
        this.bobbers = new Value<Boolean>("Bobbers", new String[] { "Bobb", "Bob", "FishHook", "FishHooks" }, "Apply velocity modifier on fishing bobber velocity.", true);
        this.NoPush = new Value<Boolean>("NoPush", new String[] { "AntiPush" }, "Disable collision with entities, blocks and water", true);
        this.PushOutOfBlocks = new Listener<EventPlayerPushOutOfBlocks>(p_Event -> {
            if (!this.NoPush.getValue()) {
                return;
            }
            else {
                p_Event.cancel();
                return;
            }
        }, (Predicate<EventPlayerPushOutOfBlocks>[])new Predicate[0]);
        this.PushByWater = new Listener<EventPlayerPushedByWater>(p_Event -> {
            if (!this.NoPush.getValue()) {
                return;
            }
            else {
                p_Event.cancel();
                return;
            }
        }, (Predicate<EventPlayerPushedByWater>[])new Predicate[0]);
        this.ApplyCollision = new Listener<EventPlayerApplyCollision>(p_Event -> {
            if (!this.NoPush.getValue()) {
                return;
            }
            else {
                p_Event.cancel();
                return;
            }
        }, (Predicate<EventPlayerApplyCollision>[])new Predicate[0]);
        SPacketEntityStatus packet;
        Entity entity;
        EntityFishHook fishHook;
        SPacketEntityVelocity packet2;
        SPacketExplosion packet3;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (this.mc.player != null) {
                if (p_Event.getPacket() instanceof SPacketEntityStatus && this.bobbers.getValue()) {
                    packet = (SPacketEntityStatus)p_Event.getPacket();
                    if (packet.getOpCode() == 31) {
                        entity = packet.getEntity((World)Minecraft.getMinecraft().world);
                        if (entity != null && entity instanceof EntityFishHook) {
                            fishHook = (EntityFishHook)entity;
                            if (fishHook.caughtEntity == Minecraft.getMinecraft().player) {
                                p_Event.cancel();
                            }
                        }
                    }
                }
                if (p_Event.getPacket() instanceof SPacketEntityVelocity) {
                    packet2 = (SPacketEntityVelocity)p_Event.getPacket();
                    if (packet2.getEntityID() == this.mc.player.getEntityId()) {
                        if (this.horizontal_vel.getValue() == 0 && this.vertical_vel.getValue() == 0) {
                            p_Event.cancel();
                            return;
                        }
                        else {
                            if (this.horizontal_vel.getValue() != 100) {
                                packet2.motionX = packet2.motionX / 100 * this.horizontal_vel.getValue();
                                packet2.motionZ = packet2.motionZ / 100 * this.horizontal_vel.getValue();
                            }
                            if (this.vertical_vel.getValue() != 100) {
                                packet2.motionY = packet2.motionY / 100 * this.vertical_vel.getValue();
                            }
                        }
                    }
                }
                if (p_Event.getPacket() instanceof SPacketExplosion && this.explosions.getValue()) {
                    packet3 = (SPacketExplosion)p_Event.getPacket();
                    if (this.horizontal_vel.getValue() == 0 && this.vertical_vel.getValue() == 0) {
                        p_Event.cancel();
                    }
                    else {
                        if (this.horizontal_vel.getValue() != 100) {
                            packet3.motionX = packet3.motionX / 100.0f * this.horizontal_vel.getValue();
                            packet3.motionZ = packet3.motionZ / 100.0f * this.horizontal_vel.getValue();
                        }
                        if (this.vertical_vel.getValue() != 100) {
                            packet3.motionY = packet3.motionY / 100.0f * this.vertical_vel.getValue();
                        }
                    }
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return String.format("H:%s%% V:%s%%", this.horizontal_vel.getValue(), this.vertical_vel.getValue());
    }
}
