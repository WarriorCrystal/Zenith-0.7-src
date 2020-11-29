//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.Packet;
import java.util.LinkedList;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class BlinkModule extends Module
{
    public final Value<Boolean> Visualize;
    public final Value<Boolean> EntityBlink;
    private EntityOtherPlayerMP Original;
    private EntityDonkey RidingEntity;
    private LinkedList<Packet> Packets;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public BlinkModule() {
        super("Blink", new String[] { "FakeLag" }, "Holds move packets until disabled", "NONE", -1, ModuleType.MOVEMENT);
        this.Visualize = new Value<Boolean>("Visualize", new String[] { "FakePlayer" }, "Visualizes your body while blink is enabled", true);
        this.EntityBlink = new Value<Boolean>("EntityBlink", new String[] { "Vehicles" }, "Holds the CPacketVehicleMove", true);
        this.Packets = new LinkedList<Packet>();
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof CPacketPlayer || p_Event.getPacket() instanceof CPacketConfirmTeleport || (this.EntityBlink.getValue() && p_Event.getPacket() instanceof CPacketVehicleMove)) {
                p_Event.cancel();
                this.Packets.add(p_Event.getPacket());
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.Packets.clear();
        this.Original = null;
        this.RidingEntity = null;
        if (this.Visualize.getValue()) {
            (this.Original = new EntityOtherPlayerMP((World)this.mc.world, this.mc.session.getProfile())).copyLocationAndAnglesFrom((Entity)this.mc.player);
            this.Original.rotationYaw = this.mc.player.rotationYaw;
            this.Original.rotationYawHead = this.mc.player.rotationYawHead;
            this.Original.inventory.copyInventory(this.mc.player.inventory);
            this.mc.world.addEntityToWorld(-1048575, (Entity)this.Original);
            if (this.mc.player.isRiding() && this.mc.player.getRidingEntity() instanceof EntityDonkey) {
                final EntityDonkey l_Original = (EntityDonkey)this.mc.player.getRidingEntity();
                (this.RidingEntity = new EntityDonkey((World)this.mc.world)).copyLocationAndAnglesFrom((Entity)l_Original);
                this.RidingEntity.setChested(l_Original.hasChest());
                this.mc.world.addEntityToWorld(-1048574, (Entity)this.RidingEntity);
                this.Original.startRiding((Entity)this.RidingEntity, true);
            }
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (!this.Packets.isEmpty() && this.mc.world != null) {
            while (!this.Packets.isEmpty()) {
                this.mc.getConnection().sendPacket((Packet)this.Packets.getFirst());
                this.Packets.removeFirst();
            }
        }
        if (this.Original != null) {
            if (this.Original.isRiding()) {
                this.Original.dismountRidingEntity();
            }
            this.mc.world.removeEntity((Entity)this.Original);
        }
        if (this.RidingEntity != null) {
            this.mc.world.removeEntity((Entity)this.RidingEntity);
        }
    }
}
