//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.ui;

import net.minecraft.client.gui.GuiScreen;
import me.gopro336.goprohack.managers.ModuleManager;
import me.gopro336.goprohack.gui.click.ClickGuiScreen;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class ClickGuiModule extends Module
{
    public final Value<Boolean> AllowOverflow;
    public final Value<Boolean> Watermark;
    public final Value<Boolean> HoverDescriptions;
    public final Value<Boolean> Snowing;
    public ClickGuiScreen m_ClickGui;
    
    public ClickGuiModule() {
        super("ClickGui", new String[] { "ClickGui", "ClickGui" }, "Displays the click gui", "P", 5131167, ModuleType.UI);
        this.AllowOverflow = new Value<Boolean>("AllowOverflow", new String[] { "AllowOverflow" }, "Allows the GUI to overflow", true);
        this.Watermark = new Value<Boolean>("Watermark", new String[] { "Watermark" }, "Displays the watermark on the GUI", true);
        this.HoverDescriptions = new Value<Boolean>("HoverDescriptions", new String[] { "HD" }, "Displays hover descriptions over values and modules", true);
        this.Snowing = new Value<Boolean>("Snowing", new String[] { "SN" }, "Play a snowing animation in ClickGUI", true);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.m_ClickGui == null) {
            this.m_ClickGui = new ClickGuiScreen(this, (ColorsModule)ModuleManager.Get().GetMod(ColorsModule.class));
        }
        if (this.mc.world != null) {
            this.mc.displayGuiScreen((GuiScreen)this.m_ClickGui);
        }
    }
}
