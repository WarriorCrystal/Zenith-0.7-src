//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.command.Command;

public class SoundReloadCommand extends Command
{
    public SoundReloadCommand() {
        super("SoundReload", "Reloads the sound system");
    }
    
    @Override
    public void ProcessCommand(final String p_Args) {
        this.mc.getSoundHandler().sndManager.reloadSoundSystem();
        this.SendToChat(ChatFormatting.GREEN + "Reloaded the SoundSystem!");
    }
    
    @Override
    public String GetHelp() {
        return "Reloads the sound manager sound system";
    }
}
