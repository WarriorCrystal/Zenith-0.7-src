//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import java.util.function.Predicate;
import me.gopro336.goprohack.main.GoproHack;
import net.minecraft.network.play.server.SPacketSpawnMob;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class DonkeySpawnNotifierModule extends Module
{
    public final Value<Boolean> Donkey;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public DonkeySpawnNotifierModule() {
        super("DonkeySpawnNotif", new String[] { "DonkFinderModule" }, "Donkey Finder", "NONE", 5131167, ModuleType.MISC);
        this.Donkey = new Value<Boolean>("Donkey", new String[] { "Donkey" }, "logs location of donkey spawns", false);
        SPacketSpawnMob packet;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof SPacketSpawnMob) {
                packet = (SPacketSpawnMob)p_Event.getPacket();
                if (this.Donkey.getValue() && packet.getEntityType() == 31) {
                    GoproHack.SendMessage(String.format("Donkey spawned at %s %s %s", packet.getX(), packet.getY(), packet.getZ()));
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
}
