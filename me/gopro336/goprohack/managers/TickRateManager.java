//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.managers;

import me.gopro336.goprohack.main.GoproHack;
import me.gopro336.goprohack.GoproHackMod;
import java.util.function.Predicate;
import net.minecraft.util.math.MathHelper;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.Listener;
import me.zero.alpine.fork.listener.Listenable;

public class TickRateManager implements Listenable
{
    private long prevTime;
    private float[] ticks;
    private int currentTick;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public TickRateManager() {
        this.ticks = new float[20];
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.GetPacket() instanceof SPacketTimeUpdate) {
                if (this.prevTime != -1L) {
                    this.ticks[this.currentTick % this.ticks.length] = MathHelper.clamp(20.0f / ((System.currentTimeMillis() - this.prevTime) / 1000.0f), 0.0f, 20.0f);
                    ++this.currentTick;
                }
                this.prevTime = System.currentTimeMillis();
            }
            return;
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
        this.prevTime = -1L;
        for (int i = 0, len = this.ticks.length; i < len; ++i) {
            this.ticks[i] = 0.0f;
        }
        GoproHackMod.EVENT_BUS.subscribe(this);
    }
    
    public float getTickRate() {
        int tickCount = 0;
        float tickRate = 0.0f;
        for (int i = 0; i < this.ticks.length; ++i) {
            final float tick = this.ticks[i];
            if (tick > 0.0f) {
                tickRate += tick;
                ++tickCount;
            }
        }
        return MathHelper.clamp(tickRate / tickCount, 0.0f, 20.0f);
    }
    
    public static TickRateManager Get() {
        return GoproHack.GetTickRateManager();
    }
}
