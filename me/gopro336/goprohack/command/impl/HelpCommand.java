// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.command.impl;

import java.util.List;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.managers.CommandManager;
import me.gopro336.goprohack.command.Command;

public class HelpCommand extends Command
{
    public HelpCommand() {
        super("Help", "Gives you help for commands");
    }
    
    @Override
    public void ProcessCommand(final String p_Args) {
        final String[] l_Split = p_Args.split(" ");
        if (l_Split == null || l_Split.length <= 1) {
            this.SendToChat(this.GetHelp());
            return;
        }
        final Command l_Command = CommandManager.Get().GetCommandLike(l_Split[1]);
        if (l_Command == null) {
            this.SendToChat(String.format("Couldn't find any command named like %s", l_Split[1]));
        }
        else {
            this.SendToChat(l_Command.GetHelp());
        }
    }
    
    @Override
    public String GetHelp() {
        final List<Command> l_Commands = CommandManager.Get().GetCommands();
        String l_CommandString = "Available commands: (" + l_Commands.size() + ")" + ChatFormatting.WHITE + " [";
        for (int l_I = 0; l_I < l_Commands.size(); ++l_I) {
            final Command l_Command = l_Commands.get(l_I);
            if (l_I == l_Commands.size() - 1) {
                l_CommandString = l_CommandString + l_Command.GetName() + "]";
            }
            else {
                l_CommandString = l_CommandString + l_Command.GetName() + ", ";
            }
        }
        return l_CommandString;
    }
}
