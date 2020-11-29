//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import me.gopro336.goprohack.events.MinecraftEvent;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class SneakModule extends Module
{
    public final Value<Mode> mode;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public SneakModule() {
        super("Sneak", new String[] { "Sneek" }, "Allows you to sneak at full speed", "NONE", 5131167, ModuleType.MOVEMENT);
        this.mode = new Value<Mode>("Mode", new String[] { "Mode", "M" }, "The sneak mode to use.", Mode.NCP);
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() != MinecraftEvent.Era.PRE) {
                return;
            }
            else {
                switch (this.mode.getValue()) {
                    case Vanilla: {
                        this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                        break;
                    }
                    case NCP: {
                        if (!this.mc.player.isSneaking()) {
                            if (this.isMoving()) {
                                this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                                this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                                break;
                            }
                            else {
                                this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                                break;
                            }
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Always: {
                        this.mc.gameSettings.keyBindSneak.pressed = true;
                        break;
                    }
                }
                return;
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (this.mode.getValue() != Mode.Always && p_Event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && !this.mc.player.isSneaking()) {
                this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mc.world != null && !this.mc.player.isSneaking()) {
            this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    @Override
    public String getMetaData() {
        return this.mode.getValue().name();
    }
    
    private boolean isMoving() {
        return GameSettings.isKeyDown(this.mc.gameSettings.keyBindForward) || GameSettings.isKeyDown(this.mc.gameSettings.keyBindLeft) || GameSettings.isKeyDown(this.mc.gameSettings.keyBindRight) || GameSettings.isKeyDown(this.mc.gameSettings.keyBindBack);
    }
    
    private enum Mode
    {
        Vanilla, 
        NCP, 
        Always;
    }
}
