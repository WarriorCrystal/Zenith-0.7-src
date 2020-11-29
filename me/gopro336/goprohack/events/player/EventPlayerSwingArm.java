// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.player;

import net.minecraft.util.EnumHand;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventPlayerSwingArm extends MinecraftEvent
{
    public EnumHand Hand;
    
    public EventPlayerSwingArm(final EnumHand p_Hand) {
        this.Hand = p_Hand;
    }
}
