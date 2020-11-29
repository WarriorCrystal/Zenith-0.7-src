// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.command.impl;

import me.gopro336.goprohack.managers.FontManager;
import java.io.IOException;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.io.File;
import me.gopro336.goprohack.managers.DirectoryManager;
import me.gopro336.goprohack.command.Command;

public class FontCommand extends Command
{
    public FontCommand() {
        super("Font", "Allows you to set the font of the client, this must be an existing TTF in your goprohack folder");
        this.CommandChunks.add("<ttfFontName>");
    }
    
    @Override
    public void ProcessCommand(final String args) {
        final String[] split = args.split(" ");
        if (split == null || split.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        if (split.length <= 2) {
            this.SendToChat(String.format("Trying to load \"%s\"", split[1]));
            try {
                if (!new File(DirectoryManager.Get().GetCurrentDirectory() + "/GoproHack/Fonts/" + split[1] + ".ttf").exists()) {
                    this.SendToChat(ChatFormatting.RED + "That file doesn't exist in GoproHack/Fonts/ directory!");
                    return;
                }
            }
            catch (IOException ex) {}
            FontManager.Get().LoadCustomFont(split[1]);
        }
    }
    
    @Override
    public String GetHelp() {
        return "Allows you to Bind a mod";
    }
}
