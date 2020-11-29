// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.player;

import net.minecraft.entity.Entity;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventPlayerApplyCollision extends MinecraftEvent
{
    public Entity entity;
    
    public EventPlayerApplyCollision(final Entity p_Entity) {
        this.entity = p_Entity;
    }
}
