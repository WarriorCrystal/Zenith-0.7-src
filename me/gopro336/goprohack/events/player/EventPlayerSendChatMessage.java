// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.player;

import me.gopro336.goprohack.events.MinecraftEvent;

public class EventPlayerSendChatMessage extends MinecraftEvent
{
    public String Message;
    
    public EventPlayerSendChatMessage(final String p_Message) {
        this.Message = p_Message;
    }
}
