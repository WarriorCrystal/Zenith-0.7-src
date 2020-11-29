// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.player;

import me.gopro336.goprohack.events.MinecraftEvent;

public class EventPlayerLeave extends MinecraftEvent
{
    private String _name;
    private String _id;
    
    public EventPlayerLeave(final String name, final String id) {
        this._name = name;
        this._id = id;
    }
    
    public String getName() {
        return this._name;
    }
    
    public String getId() {
        return this._id;
    }
}
