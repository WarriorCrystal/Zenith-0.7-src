// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.render;

import me.gopro336.goprohack.events.MinecraftEvent;

public class EventRenderHand extends MinecraftEvent
{
    public float PartialTicks;
    public int Pass;
    
    public EventRenderHand(final float partialTicks, final int pass) {
        this.PartialTicks = partialTicks;
        this.Pass = pass;
    }
}
