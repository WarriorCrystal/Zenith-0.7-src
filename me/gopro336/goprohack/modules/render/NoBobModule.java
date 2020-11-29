//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import java.util.function.Predicate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Module;

public class NoBobModule extends Module
{
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public NoBobModule() {
        super("NoBob", new String[] { "NoBob" }, "Prevents bobbing by setting distance walked modifier to a static number", "NONE", -1, ModuleType.RENDER);
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> this.mc.player.distanceWalkedModified = 4.0f, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
    }
}
