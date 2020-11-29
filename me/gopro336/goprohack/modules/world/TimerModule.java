//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.world;

import net.minecraft.network.play.server.SPacketPlayerPosLook;
import java.util.function.Predicate;
import me.gopro336.goprohack.managers.TickRateManager;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import java.text.DecimalFormat;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class TimerModule extends Module
{
    public final Value<Float> speed;
    public final Value<Boolean> Accelerate;
    public final Value<Boolean> TPSSync;
    private Timer timer;
    private float OverrideSpeed;
    private DecimalFormat l_Format;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public TimerModule() {
        super("Timer", new String[] { "Time", "Tmr" }, "Speeds up the client tick rate", "NONE", 2415523, ModuleType.WORLD);
        this.speed = new Value<Float>("Speed", new String[] { "Spd" }, "Tick-rate multiplier. [(20tps/second) * (this value)]", 4.0f, 0.1f, 10.0f, 0.1f);
        this.Accelerate = new Value<Boolean>("Accelerate", new String[] { "Acc" }, "Accelerate's from 1.0 until the anticheat lags you back", false);
        this.TPSSync = new Value<Boolean>("TPSSync", new String[] { "TPS" }, "Syncs the game time to the current TPS", false);
        this.timer = new Timer();
        this.OverrideSpeed = 1.0f;
        this.l_Format = new DecimalFormat("#.#");
        float l_TPS;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.OverrideSpeed != 1.0f && this.OverrideSpeed > 0.1f) {
                this.mc.timer.tickLength = 50.0f / this.OverrideSpeed;
                return;
            }
            else {
                if (this.TPSSync.getValue()) {
                    l_TPS = TickRateManager.Get().getTickRate();
                    this.mc.timer.tickLength = Math.min(500.0f, 50.0f * (20.0f / l_TPS));
                }
                else {
                    this.mc.timer.tickLength = 50.0f / this.GetSpeed();
                }
                if (this.Accelerate.getValue() && this.timer.passed(2000.0)) {
                    this.timer.reset();
                    this.speed.setValue(this.speed.getValue() + 0.1f);
                }
                return;
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof SPacketPlayerPosLook && this.Accelerate.getValue()) {
                this.speed.setValue(1.0f);
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.timer.tickLength = 50.0f;
    }
    
    @Override
    public String getMetaData() {
        if (this.OverrideSpeed != 1.0f) {
            return String.valueOf(this.OverrideSpeed);
        }
        if (this.TPSSync.getValue()) {
            final float l_TPS = TickRateManager.Get().getTickRate();
            return this.l_Format.format(l_TPS / 20.0f);
        }
        return this.l_Format.format(this.GetSpeed());
    }
    
    private float GetSpeed() {
        return Math.max(this.speed.getValue(), 0.1f);
    }
    
    public void SetOverrideSpeed(final float f) {
        this.OverrideSpeed = f;
    }
}
