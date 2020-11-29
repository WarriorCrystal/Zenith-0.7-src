//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.handler;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.client.gui.GuiScreen;

public class DelayedGuiDisplayTicker
{
    private final GuiScreen guiScreen;
    private int ticks;
    
    private DelayedGuiDisplayTicker(final GuiScreen guiScreen, final int delay) {
        this.guiScreen = guiScreen;
        this.ticks = delay;
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        --this.ticks;
        if (this.ticks < 0) {
            Minecraft.getMinecraft().displayGuiScreen(this.guiScreen);
            MinecraftForge.EVENT_BUS.unregister((Object)this);
        }
    }
    
    public static void create(final GuiScreen guiScreen, final int delay) {
        MinecraftForge.EVENT_BUS.register((Object)new DelayedGuiDisplayTicker(guiScreen, delay));
    }
}
