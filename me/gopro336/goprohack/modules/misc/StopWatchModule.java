// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import java.util.function.Predicate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.client.EventClientTick;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Module;

public class StopWatchModule extends Module
{
    public long StartMS;
    public long ElapsedMS;
    @EventHandler
    private Listener<EventClientTick> OnTick;
    
    public StopWatchModule() {
        super("Stopwatch", new String[] { "" }, "Counts a stopwatch starting from 0 when toggled.", "NONE", -1, ModuleType.MISC);
        this.OnTick = new Listener<EventClientTick>(p_Event -> this.ElapsedMS = System.currentTimeMillis(), (Predicate<EventClientTick>[])new Predicate[0]);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.StartMS = System.currentTimeMillis();
        this.ElapsedMS = System.currentTimeMillis();
    }
}
