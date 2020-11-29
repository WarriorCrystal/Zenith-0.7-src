//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.minecraft;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import me.gopro336.goprohack.main.AlwaysEnabledModule;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import me.gopro336.goprohack.managers.ModuleManager;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.misc.AutoReconnectModule;
import net.minecraft.client.gui.GuiButton;

public class GoproGuiReconnectButton extends GuiButton
{
    private AutoReconnectModule Mod;
    private Timer timer;
    private float ReconnectTimer;
    
    public GoproGuiReconnectButton(final int buttonId, final int x, final int y, final String buttonText) {
        super(buttonId, x, y, buttonText);
        (this.timer = new Timer()).reset();
        this.Mod = (AutoReconnectModule)ModuleManager.Get().GetMod(AutoReconnectModule.class);
        this.ReconnectTimer = this.Mod.Delay.getValue() * 1000.0f;
    }
    
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks) {
        super.drawButton(mc, mouseX, mouseY, partialTicks);
        if (this.visible) {
            if (this.Mod.isEnabled() && !this.timer.passed(this.ReconnectTimer)) {
                this.displayString = "AutoReconnect (" + TimeUnit.MILLISECONDS.toSeconds(Math.abs(this.timer.getTime() + (long)this.ReconnectTimer - System.currentTimeMillis())) + ")";
            }
            else if (!this.Mod.isEnabled()) {
                this.displayString = "AutoReconnect";
            }
            if (this.timer.passed(this.ReconnectTimer) && this.Mod.isEnabled() && AlwaysEnabledModule.LastIP != null && AlwaysEnabledModule.LastPort != -1) {
                mc.displayGuiScreen((GuiScreen)new GuiConnecting((GuiScreen)null, mc, AlwaysEnabledModule.LastIP, AlwaysEnabledModule.LastPort));
            }
        }
    }
    
    public void Clicked() {
        this.Mod.toggle();
        this.timer.reset();
    }
}
