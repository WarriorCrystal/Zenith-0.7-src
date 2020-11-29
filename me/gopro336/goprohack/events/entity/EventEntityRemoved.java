// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.entity;

import net.minecraft.entity.Entity;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventEntityRemoved extends MinecraftEvent
{
    private Entity m_Entity;
    
    public EventEntityRemoved(final Entity p_Entity) {
        this.m_Entity = p_Entity;
    }
    
    public Entity GetEntity() {
        return this.m_Entity;
    }
}
