// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.player;

import net.minecraft.entity.MoverType;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventPlayerMove extends MinecraftEvent
{
    public MoverType Type;
    public double X;
    public double Y;
    public double Z;
    
    public EventPlayerMove(final MoverType p_Type, final double p_X, final double p_Y, final double p_Z) {
        this.Type = p_Type;
        this.X = p_X;
        this.Y = p_Y;
        this.Z = p_Z;
    }
}
