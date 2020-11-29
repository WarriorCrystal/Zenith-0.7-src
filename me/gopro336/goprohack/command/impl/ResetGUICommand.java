// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.command.impl;

import me.gopro336.goprohack.managers.ModuleManager;
import me.gopro336.goprohack.modules.ui.ClickGuiModule;
import me.gopro336.goprohack.command.Command;

public class ResetGUICommand extends Command
{
    public ResetGUICommand() {
        super("ResetGUI", "Reset the ClickGUI positions to default");
    }
    
    @Override
    public void ProcessCommand(final String p_Args) {
        final ClickGuiModule mod = (ClickGuiModule)ModuleManager.Get().GetMod(ClickGuiModule.class);
        if (mod != null) {
            mod.m_ClickGui.ResetToDefaults();
        }
        this.SendToChat("Reset the ClickGUI");
    }
    
    @Override
    public String GetHelp() {
        return "Allows you teleport up x amount of blocks.";
    }
}
