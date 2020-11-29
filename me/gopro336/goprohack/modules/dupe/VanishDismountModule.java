//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.dupe;

import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketSetPassengers;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import me.gopro336.goprohack.modules.Module;

public final class VanishDismountModule extends Module
{
    private Entity Riding;
    @EventHandler
    private Listener<EventPlayerUpdate> OnUpdate;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    @EventHandler
    private Listener<EntityJoinWorldEvent> OnWorldEvent;
    
    public VanishDismountModule() {
        super("VanishDismount", new String[] { "VD" }, "Vanish dismounts from entity", "NONE", 14396196, ModuleType.DUPE);
        this.Riding = null;
        this.OnUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.Riding == null) {
                return;
            }
            else if (this.mc.player.isRiding()) {
                return;
            }
            else {
                this.mc.player.onGround = true;
                this.Riding.setPosition(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
                this.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove(this.Riding));
                return;
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        SPacketSetPassengers l_Packet;
        Entity en;
        final int[] array;
        int length;
        int j = 0;
        int i;
        Entity ent;
        SPacketDestroyEntities l_Packet2;
        final int[] array2;
        int length2;
        int k = 0;
        int l_EntityId;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof SPacketSetPassengers) {
                if (this.Riding != null) {
                    l_Packet = (SPacketSetPassengers)p_Event.getPacket();
                    en = this.mc.world.getEntityByID(l_Packet.getEntityId());
                    if (en == this.Riding) {
                        l_Packet.getPassengerIds();
                        length = array.length;
                        while (j < length) {
                            i = array[j];
                            ent = this.mc.world.getEntityByID(i);
                            if (ent == this.mc.player) {
                                return;
                            }
                            else {
                                ++j;
                            }
                        }
                        this.SendMessage("You dismounted. RIP");
                        this.toggle();
                    }
                }
            }
            else if (p_Event.getPacket() instanceof SPacketDestroyEntities) {
                l_Packet2 = (SPacketDestroyEntities)p_Event.getPacket();
                l_Packet2.getEntityIDs();
                length2 = array2.length;
                while (k < length2) {
                    l_EntityId = array2[k];
                    if (l_EntityId == this.Riding.getEntityId()) {
                        this.SendMessage("Entity is now null SPacketDestroyEntities");
                    }
                    else {
                        ++k;
                    }
                }
            }
            return;
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
        this.OnWorldEvent = new Listener<EntityJoinWorldEvent>(p_Event -> {
            if (p_Event.getEntity() == this.mc.player) {
                this.SendMessage("Joined world event!");
            }
        }, (Predicate<EntityJoinWorldEvent>[])new Predicate[0]);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.player == null) {
            this.Riding = null;
            this.toggle();
            return;
        }
        if (!this.mc.player.isRiding()) {
            this.SendMessage("You are not riding an entity.");
            this.Riding = null;
            this.toggle();
            return;
        }
        this.Riding = this.mc.player.getRidingEntity();
        this.mc.player.dismountRidingEntity();
        this.mc.world.removeEntity(this.Riding);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.Riding != null) {
            this.Riding.isDead = false;
            if (!this.mc.player.isRiding()) {
                this.mc.world.spawnEntity(this.Riding);
                this.mc.player.startRiding(this.Riding, true);
            }
            this.Riding = null;
            this.SendMessage("Forced a remount.");
        }
    }
}
