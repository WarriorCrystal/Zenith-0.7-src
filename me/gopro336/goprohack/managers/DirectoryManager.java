// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.managers;

import me.gopro336.goprohack.main.GoproHack;
import java.io.File;
import java.io.IOException;

public class DirectoryManager
{
    public void Init() {
        try {
            this.CreateDirectory("GoproHack");
            this.CreateDirectory("GoproHack/Modules");
            this.CreateDirectory("GoproHack/GUI");
            this.CreateDirectory("GoproHack/HUD");
            this.CreateDirectory("GoproHack/Locater");
            this.CreateDirectory("GoproHack/StashFinder");
            this.CreateDirectory("GoproHack/Config");
            this.CreateDirectory("GoproHack/Capes");
            this.CreateDirectory("GoproHack/Music");
            this.CreateDirectory("GoproHack/CoordExploit");
            this.CreateDirectory("GoproHack/LogoutSpots");
            this.CreateDirectory("GoproHack/DeathSpots");
            this.CreateDirectory("GoproHack/Waypoints");
            this.CreateDirectory("GoproHack/Fonts");
            this.CreateDirectory("GoproHack/CustomMods");
            this.CreateDirectory("GoproHack/Presets");
            this.CreateDirectory("GoproHack/Presets/Default");
            this.CreateDirectory("GoproHack/Presets/Default/Modules");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void CreateDirectory(final String p_Path) throws IOException {
        new File(p_Path).mkdirs();
    }
    
    public static DirectoryManager Get() {
        return GoproHack.GetDirectoryManager();
    }
    
    public String GetCurrentDirectory() throws IOException {
        return new File(".").getCanonicalPath();
    }
}
