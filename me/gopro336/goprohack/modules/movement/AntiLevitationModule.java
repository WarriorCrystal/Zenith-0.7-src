//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import java.util.function.Predicate;
import net.minecraft.init.MobEffects;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerIsPotionActive;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Module;

public final class AntiLevitationModule extends Module
{
    @EventHandler
    private Listener<EventPlayerIsPotionActive> IsPotionActive;
    
    public AntiLevitationModule() {
        super("AntiLevitation", new String[] { "NoLevitate" }, "Prevents you from levitating", "NONE", 5131167, ModuleType.MOVEMENT);
        this.IsPotionActive = new Listener<EventPlayerIsPotionActive>(p_Event -> {
            if (p_Event.potion == MobEffects.LEVITATION) {
                p_Event.cancel();
            }
        }, (Predicate<EventPlayerIsPotionActive>[])new Predicate[0]);
    }
}
