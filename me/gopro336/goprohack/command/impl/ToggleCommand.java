// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.command.impl;

import me.gopro336.goprohack.modules.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.managers.ModuleManager;
import me.gopro336.goprohack.command.Command;

public class ToggleCommand extends Command
{
    public ToggleCommand() {
        super("Toggle", "Allows you to toggle a mod");
    }
    
    @Override
    public void ProcessCommand(final String p_Args) {
        final String[] l_Split = p_Args.split(" ");
        if (l_Split == null || l_Split.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        final Module l_Mod = ModuleManager.Get().GetModLike(l_Split[1]);
        if (l_Mod != null) {
            l_Mod.toggle();
            this.SendToChat(String.format("%sToggled %s", l_Mod.isEnabled() ? ChatFormatting.GREEN : ChatFormatting.RED, l_Mod.GetArrayListDisplayName()));
        }
        else {
            this.SendToChat(String.format("Could not find the module named %s", l_Split[1]));
        }
    }
    
    @Override
    public String GetHelp() {
        return "Allows you to toggle a mod";
    }
}
