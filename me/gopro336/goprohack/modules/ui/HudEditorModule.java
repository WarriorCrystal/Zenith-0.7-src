//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.ui;

import net.minecraft.client.gui.GuiScreen;
import me.gopro336.goprohack.gui.hud.GuiHudEditor;
import me.gopro336.goprohack.modules.Module;

public final class HudEditorModule extends Module
{
    private GuiHudEditor m_HudEditor;
    
    public HudEditorModule() {
        super("HudEditor", new String[] { "HudEditor" }, "Displays the HudEditor", "NONE", 5131167, ModuleType.UI);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onToggle() {
        super.onToggle();
        if (this.mc.world != null) {
            if (this.m_HudEditor == null) {
                this.m_HudEditor = new GuiHudEditor(this);
            }
            this.mc.displayGuiScreen((GuiScreen)this.m_HudEditor);
        }
    }
}
