//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events;

import me.gopro336.goprohack.main.Wrapper;
import me.zero.alpine.fork.event.type.Cancellable;

public class MinecraftEvent extends Cancellable
{
    private Era era;
    private final float partialTicks;
    
    public MinecraftEvent() {
        this.era = Era.PRE;
        this.partialTicks = Wrapper.GetMC().getRenderPartialTicks();
    }
    
    public MinecraftEvent(final Era p_Era) {
        this.era = Era.PRE;
        this.partialTicks = Wrapper.GetMC().getRenderPartialTicks();
        this.era = p_Era;
    }
    
    public Era getEra() {
        return this.era;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public enum Era
    {
        PRE, 
        PERI, 
        POST;
    }
}
