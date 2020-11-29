//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.ui;

import net.minecraft.client.gui.GuiScreen;
import me.gopro336.goprohack.gui.chat.GoproGuiConsole;
import me.gopro336.goprohack.modules.Module;

public final class ConsoleModule extends Module
{
    private GoproGuiConsole m_Console;
    
    public ConsoleModule() {
        super("Console", new String[] { "Console" }, "Displays the click gui", "NONE", 14397476, ModuleType.UI);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onToggle() {
        super.onToggle();
        if (this.mc.world != null) {
            if (this.m_Console == null) {
                this.m_Console = new GoproGuiConsole(this);
            }
            this.mc.displayGuiScreen((GuiScreen)this.m_Console);
        }
    }
}
