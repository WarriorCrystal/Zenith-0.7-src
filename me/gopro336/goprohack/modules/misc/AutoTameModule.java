//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import net.minecraft.network.Packet;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.Entity;
import java.util.function.Predicate;
import net.minecraft.world.World;
import net.minecraft.network.play.client.CPacketUseEntity;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import net.minecraft.entity.passive.AbstractHorse;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class AutoTameModule extends Module
{
    public final Value<Float> Delay;
    private AbstractHorse EntityToTame;
    private Timer timer;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public AutoTameModule() {
        super("AutoTame", new String[] { "" }, "Automatically tames the animal you click", "NONE", 14361796, ModuleType.MISC);
        this.Delay = new Value<Float>("Delay", new String[] { "D" }, "Delay to remount", 0.1f, 0.0f, 1.0f, 0.1f);
        this.EntityToTame = null;
        this.timer = new Timer();
        CPacketUseEntity l_Packet;
        Entity l_Entity;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof CPacketUseEntity) {
                if (this.EntityToTame == null) {
                    l_Packet = (CPacketUseEntity)p_Event.getPacket();
                    l_Entity = l_Packet.getEntityFromWorld((World)this.mc.world);
                    if (l_Entity instanceof AbstractHorse && !((AbstractHorse)l_Entity).isTame()) {
                        this.EntityToTame = (AbstractHorse)l_Entity;
                        this.SendMessage("Will try to tame " + l_Entity.getName());
                    }
                }
            }
            return;
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.EntityToTame != null) {
                if (this.EntityToTame.isTame()) {
                    this.SendMessage("Successfully tamed " + this.EntityToTame.getName() + ", disabling.");
                    this.toggle();
                }
                else if (!this.mc.player.isRiding()) {
                    if (this.mc.player.getDistance((Entity)this.EntityToTame) <= 5.0f) {
                        if (!(!this.timer.passed(this.Delay.getValue() * 1000.0f))) {
                            this.timer.reset();
                            this.mc.getConnection().sendPacket((Packet)new CPacketUseEntity((Entity)this.EntityToTame, EnumHand.MAIN_HAND));
                        }
                    }
                }
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public String getMetaData() {
        if (this.EntityToTame == null) {
            return null;
        }
        return this.EntityToTame.getName();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.SendMessage("Right click an animal you want to tame");
        this.EntityToTame = null;
    }
}
