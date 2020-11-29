//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.main;

import me.gopro336.goprohack.GoproHackMod;
import net.minecraft.client.Minecraft;
import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.network.play.server.SPacketChat;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.Listener;
import me.zero.alpine.fork.listener.Listenable;

public class AlwaysEnabledModule implements Listenable
{
    public static String LastIP;
    public static int LastPort;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public AlwaysEnabledModule() {
        SPacketChat packet;
        TextComponentString component;
        C00Handshake packet2;
        SPacketPlayerListItem packet3;
        Minecraft mc;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof SPacketChat) {
                packet = (SPacketChat)p_Event.getPacket();
                if (packet.getChatComponent() instanceof TextComponentString) {
                    component = (TextComponentString)packet.getChatComponent();
                    if (component.getFormattedText().toLowerCase().contains("polymore") || component.getFormattedText().toLowerCase().contains("veteranhack")) {
                        p_Event.cancel();
                    }
                }
            }
            else if (p_Event.getPacket() instanceof C00Handshake) {
                packet2 = (C00Handshake)p_Event.getPacket();
                if (packet2.getRequestedState() == EnumConnectionState.LOGIN) {
                    AlwaysEnabledModule.LastIP = packet2.ip;
                    AlwaysEnabledModule.LastPort = packet2.port;
                }
            }
            else if (p_Event.getPacket() instanceof SPacketPlayerListItem) {
                packet3 = (SPacketPlayerListItem)p_Event.getPacket();
                mc = Wrapper.GetMC();
                if (mc.player != null && mc.player.ticksExisted >= 1000) {
                    if (packet3.getAction() == SPacketPlayerListItem.Action.ADD_PLAYER) {}
                    if (packet3.getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {}
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    public void init() {
        GoproHackMod.EVENT_BUS.subscribe(this);
    }
    
    static {
        AlwaysEnabledModule.LastIP = null;
        AlwaysEnabledModule.LastPort = -1;
    }
}
