//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.player;

import net.minecraft.network.Packet;
import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketCloseWindow;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class XCarryModule extends Module
{
    public final Value<Boolean> ForceCancel;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public XCarryModule() {
        super("XCarry", new String[] { "XCarry", "MoreInventory" }, "Allows you to carry items in your crafting and dragging slot", "NONE", 2403803, ModuleType.PLAYER);
        this.ForceCancel = new Value<Boolean>("ForceCancel", new String[] { "" }, "Forces canceling of all CPacketCloseWindow packets", false);
        CPacketCloseWindow packet;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof CPacketCloseWindow) {
                packet = (CPacketCloseWindow)p_Event.getPacket();
                if (packet.windowId == this.mc.player.inventoryContainer.windowId || this.ForceCancel.getValue()) {
                    p_Event.cancel();
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mc.world != null) {
            this.mc.player.connection.sendPacket((Packet)new CPacketCloseWindow(this.mc.player.inventoryContainer.windowId));
        }
    }
}
