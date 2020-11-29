// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.command.impl;

import me.gopro336.goprohack.modules.Module;
import me.gopro336.goprohack.managers.ModuleManager;
import me.gopro336.goprohack.command.Command;

public class BindCommand extends Command
{
    public BindCommand() {
        super("Bind", "Allows you to bind a mod to a key");
        this.CommandChunks.add("<module>");
        this.CommandChunks.add("<module> <key>");
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
            if (l_Split.length <= 2) {
                this.SendToChat(String.format("The key of %s is %s", l_Mod.getDisplayName(), l_Mod.getKey()));
                return;
            }
            l_Mod.setKey(l_Split[2].toUpperCase());
            this.SendToChat(String.format("Set the key of %s to %s", l_Mod.getDisplayName(), l_Mod.getKey()));
        }
        else {
            this.SendToChat(String.format("Could not find the module named %s", l_Split[1]));
        }
    }
    
    @Override
    public String GetHelp() {
        return "Allows you to Bind a mod";
    }
}
