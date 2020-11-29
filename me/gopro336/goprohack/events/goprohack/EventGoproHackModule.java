// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.goprohack;

import me.gopro336.goprohack.modules.Module;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventGoproHackModule extends MinecraftEvent
{
    public final Module Mod;
    
    public EventGoproHackModule(final Module p_Mod) {
        this.Mod = p_Mod;
    }
}
