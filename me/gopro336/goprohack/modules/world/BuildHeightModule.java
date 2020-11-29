//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.world;

import java.util.function.Predicate;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Module;

public final class BuildHeightModule extends Module
{
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public BuildHeightModule() {
        super("256Interact", new String[] { "BuildH", "BHeight" }, "Allows you to interact with blocks at 256 height", "NONE", 14361709, ModuleType.WORLD);
        CPacketPlayerTryUseItemOnBlock packet;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                packet = (CPacketPlayerTryUseItemOnBlock)p_Event.getPacket();
                if (packet.getPos().getY() >= 255 && packet.getDirection() == EnumFacing.UP) {
                    packet.placedBlockDirection = EnumFacing.DOWN;
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
}
