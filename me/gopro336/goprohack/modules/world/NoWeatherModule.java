//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.world;

import java.util.function.Predicate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.render.EventRenderRainStrength;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Module;

public final class NoWeatherModule extends Module
{
    @EventHandler
    private Listener<EventRenderRainStrength> OnRainStrength;
    
    public NoWeatherModule() {
        super("NoWeather", new String[] { "AntiWeather" }, "Allows you to control the weather client-side", "NONE", -1, ModuleType.WORLD);
        this.OnRainStrength = new Listener<EventRenderRainStrength>(p_Event -> {
            if (this.mc.world != null) {
                p_Event.cancel();
            }
        }, (Predicate<EventRenderRainStrength>[])new Predicate[0]);
    }
}
