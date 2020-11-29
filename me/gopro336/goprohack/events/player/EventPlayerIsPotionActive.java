// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.player;

import net.minecraft.potion.Potion;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventPlayerIsPotionActive extends MinecraftEvent
{
    public Potion potion;
    
    public EventPlayerIsPotionActive(final Potion p_Potion) {
        this.potion = p_Potion;
    }
}
