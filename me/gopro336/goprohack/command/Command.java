// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.command;

import me.gopro336.goprohack.main.GoproHack;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import me.gopro336.goprohack.main.Wrapper;
import java.util.List;
import net.minecraft.client.Minecraft;

public class Command
{
    private String Name;
    private String Description;
    protected final Minecraft mc;
    protected final List<String> CommandChunks;
    
    public Command(final String p_Name, final String p_Description) {
        this.mc = Wrapper.GetMC();
        this.CommandChunks = new ArrayList<String>();
        this.Name = p_Name;
        this.Description = p_Description;
    }
    
    public String GetName() {
        return this.Name;
    }
    
    public String GetDescription() {
        return this.Description;
    }
    
    public void ProcessCommand(final String p_Args) {
    }
    
    protected void SendToChat(final String p_Desc) {
        GoproHack.SendMessage(String.format("%s[%s]: %s", ChatFormatting.LIGHT_PURPLE, this.GetName(), ChatFormatting.YELLOW + p_Desc));
    }
    
    public List<String> GetChunks() {
        return this.CommandChunks;
    }
    
    public String GetHelp() {
        return this.Description;
    }
}
