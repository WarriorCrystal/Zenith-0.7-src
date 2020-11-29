//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketUseEntity;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class CriticalsModule extends Module
{
    public final Value<Modes> Mode;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public CriticalsModule() {
        super("Criticals", new String[] { "BS" }, "Tries to crit your oponent on attack by spoofing positions", "NONE", 5131167, ModuleType.COMBAT);
        this.Mode = new Value<Modes>("Mode", new String[] { "M" }, "Mode to change to for criticals", Modes.Packet);
        CPacketUseEntity l_Packet;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof CPacketUseEntity) {
                l_Packet = (CPacketUseEntity)p_Event.getPacket();
                if (l_Packet.getAction() == CPacketUseEntity.Action.ATTACK && l_Packet.getEntityFromWorld((World)this.mc.world) instanceof EntityLivingBase && this.mc.player.onGround && !this.mc.gameSettings.keyBindJump.isKeyDown()) {
                    switch (this.Mode.getValue()) {
                        case Jump: {
                            this.mc.player.jump();
                            break;
                        }
                        case Packet: {
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.10000000149011612, this.mc.player.posZ, false));
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, false));
                            break;
                        }
                    }
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    public enum Modes
    {
        Packet, 
        Jump;
    }
}
