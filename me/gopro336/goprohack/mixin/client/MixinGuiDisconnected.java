//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin.client;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import me.gopro336.goprohack.main.AlwaysEnabledModule;
import me.gopro336.goprohack.main.Wrapper;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.gopro336.goprohack.gui.minecraft.GoproGuiReconnectButton;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.gui.GuiDisconnected;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { GuiDisconnected.class }, priority = Integer.MAX_VALUE)
public class MixinGuiDisconnected extends MixinGuiScreen
{
    @Shadow
    public int textHeight;
    private GoproGuiReconnectButton ReconnectingButton;
    
    @Inject(method = { "initGui" }, at = { @At("RETURN") })
    public void initGui(final CallbackInfo info) {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT, this.height - 30), I18n.format("gui.toMenu", new Object[0])));
        this.buttonList.add(new GuiButton(421, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 20, this.height - 10), "Reconnect"));
        this.buttonList.add(this.ReconnectingButton = new GoproGuiReconnectButton(420, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 40, this.height + 10), "AutoReconnect"));
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("RETURN") })
    protected void actionPerformed(final GuiButton button, final CallbackInfo info) {
        if (button.id == 420) {
            this.ReconnectingButton.Clicked();
        }
        else if (button.id == 421) {
            Wrapper.GetMC().displayGuiScreen((GuiScreen)new GuiConnecting((GuiScreen)null, Wrapper.GetMC(), AlwaysEnabledModule.LastIP, AlwaysEnabledModule.LastPort));
        }
    }
    
    @Inject(method = { "drawScreen" }, at = { @At("RETURN") })
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo info) {
        if (this.buttonList.size() > 3) {
            this.buttonList.clear();
            this.buttonList.add(new GuiButton(0, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT, this.height - 30), I18n.format("gui.toMenu", new Object[0])));
            this.buttonList.add(new GuiButton(421, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 20, this.height - 10), "Reconnect"));
            this.buttonList.add(this.ReconnectingButton = new GoproGuiReconnectButton(420, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 40, this.height + 10), "AutoReconnect"));
        }
    }
}
