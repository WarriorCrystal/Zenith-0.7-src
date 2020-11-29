//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import java.util.function.Predicate;
import me.gopro336.goprohack.managers.NotificationManager;
import net.minecraft.world.World;
import net.minecraft.network.play.server.SPacketEntityStatus;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.Listener;
import java.util.HashMap;
import me.gopro336.goprohack.modules.Module;

public class TotemPopNotifierModule extends Module
{
    private HashMap<String, Integer> TotemPopContainer;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public TotemPopNotifierModule() {
        super("PopCounter", new String[] { "TPN" }, "Counts when someone pops a totem!", "NONE", 2392795, ModuleType.MISC);
        this.TotemPopContainer = new HashMap<String, Integer>();
        SPacketEntityStatus l_Packet;
        Entity l_Entity;
        int l_Count;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof SPacketEntityStatus) {
                l_Packet = (SPacketEntityStatus)p_Event.getPacket();
                if (l_Packet.getOpCode() == 35) {
                    l_Entity = l_Packet.getEntity((World)this.mc.world);
                    if (l_Entity != null) {
                        l_Count = 1;
                        if (this.TotemPopContainer.containsKey(l_Entity.getName())) {
                            l_Count = this.TotemPopContainer.get(l_Entity.getName());
                            this.TotemPopContainer.put(l_Entity.getName(), ++l_Count);
                        }
                        else {
                            this.TotemPopContainer.put(l_Entity.getName(), l_Count);
                        }
                        NotificationManager.Get().AddNotification("GoproHack", l_Entity.getName() + " popped " + l_Count + " totems! kek");
                        this.SendMessage(l_Entity.getName() + " popped " + l_Count + " totems kek");
                    }
                }
            }
            return;
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
        final Iterator<EntityPlayer> iterator;
        EntityPlayer l_Player;
        int l_Count2;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            this.mc.world.playerEntities.iterator();
            while (iterator.hasNext()) {
                l_Player = iterator.next();
                if (!this.TotemPopContainer.containsKey(l_Player.getName())) {
                    continue;
                }
                else if (l_Player.isDead || l_Player.getHealth() <= 0.0f) {
                    l_Count2 = this.TotemPopContainer.get(l_Player.getName());
                    this.TotemPopContainer.remove(l_Player.getName());
                    NotificationManager.Get().AddNotification("GoproHack", l_Player.getName() + " was EZd after popping " + l_Count2 + " totems kek");
                    this.SendMessage(l_Player.getName() + " died after popping " + l_Count2 + " totem(s)!");
                }
                else {
                    continue;
                }
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
    }
}
