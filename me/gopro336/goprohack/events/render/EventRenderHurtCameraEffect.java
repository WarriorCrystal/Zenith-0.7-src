// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.render;

import me.gopro336.goprohack.events.MinecraftEvent;

public class EventRenderHurtCameraEffect extends MinecraftEvent
{
    public float Ticks;
    
    public EventRenderHurtCameraEffect(final float p_Ticks) {
        this.Ticks = p_Ticks;
    }
}
