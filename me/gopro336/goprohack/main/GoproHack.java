//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.main;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import me.gopro336.goprohack.GoproHackMod;
import me.gopro336.goprohack.managers.UUIDManager;
import me.gopro336.goprohack.managers.PresetsManager;
import me.gopro336.goprohack.managers.CapeManager;
import me.gopro336.goprohack.waypoints.WaypointManager;
import me.gopro336.goprohack.managers.NotificationManager;
import me.gopro336.goprohack.managers.TickRateManager;
import me.gopro336.goprohack.managers.CommandManager;
import me.gopro336.goprohack.managers.DirectoryManager;
import me.gopro336.goprohack.managers.DiscordManager;
import me.gopro336.goprohack.managers.FriendManager;
import me.gopro336.goprohack.managers.HudManager;
import me.gopro336.goprohack.managers.FontManager;
import me.gopro336.goprohack.managers.ImageManager;
import me.gopro336.goprohack.managers.ModuleManager;

public class GoproHack
{
    private static ModuleManager m_ModuleManager;
    private static ImageManager m_ImageManager;
    private static FontManager m_FontManager;
    private static HudManager m_HudManager;
    private static FriendManager m_FriendManager;
    private static DiscordManager m_DiscordManager;
    private static DirectoryManager m_DirectoryManager;
    private static CommandManager m_CommandManager;
    private static TickRateManager m_TickRateManager;
    private static NotificationManager m_NotificationManager;
    private static WaypointManager m_WaypointManager;
    private static CapeManager m_CapeManager;
    private static AlwaysEnabledModule m_AlwaysEnabledMod;
    private static PresetsManager m_PresetsManager;
    private static UUIDManager m_UUIDManager;
    
    public static void Init() {
        GoproHackMod.log.info("initalizing goprohack object (all static fields)");
        GoproHack.m_DirectoryManager.Init();
        GoproHack.m_FontManager.Load();
        GoproHack.m_PresetsManager.LoadPresets();
        GoproHack.m_ModuleManager.Init();
        GoproHack.m_HudManager.Init();
        GoproHack.m_CommandManager.InitalizeCommands();
        GoproHack.m_ImageManager.Load();
        GoproHack.m_FriendManager.Load();
        (GoproHack.m_AlwaysEnabledMod = new AlwaysEnabledModule()).init();
    }
    
    public static ModuleManager GetModuleManager() {
        return GoproHack.m_ModuleManager;
    }
    
    public static ImageManager GetImageManager() {
        return GoproHack.m_ImageManager;
    }
    
    public static FontManager GetFontManager() {
        return GoproHack.m_FontManager;
    }
    
    public static void SendMessage(final String string) {
        if (Wrapper.GetMC().ingameGUI != null || Wrapper.GetPlayer() == null) {
            Wrapper.GetMC().ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString("§7[Zenith]§f " + string));
        }
    }
    
    public static HudManager GetHudManager() {
        return GoproHack.m_HudManager;
    }
    
    public static FriendManager GetFriendManager() {
        return GoproHack.m_FriendManager;
    }
    
    public static DiscordManager GetDiscordManager() {
        return GoproHack.m_DiscordManager;
    }
    
    public static DirectoryManager GetDirectoryManager() {
        return GoproHack.m_DirectoryManager;
    }
    
    public static CommandManager GetCommandManager() {
        return GoproHack.m_CommandManager;
    }
    
    public static TickRateManager GetTickRateManager() {
        return GoproHack.m_TickRateManager;
    }
    
    public static NotificationManager GetNotificationManager() {
        return GoproHack.m_NotificationManager;
    }
    
    public static WaypointManager GetWaypointManager() {
        return GoproHack.m_WaypointManager;
    }
    
    public static CapeManager GetCapeManager() {
        return GoproHack.m_CapeManager;
    }
    
    public static PresetsManager GetPresetsManager() {
        return GoproHack.m_PresetsManager;
    }
    
    public static UUIDManager GetUUIDManager() {
        return GoproHack.m_UUIDManager;
    }
    
    static {
        GoproHack.m_ModuleManager = new ModuleManager();
        GoproHack.m_ImageManager = new ImageManager();
        GoproHack.m_FontManager = new FontManager();
        GoproHack.m_HudManager = new HudManager();
        GoproHack.m_FriendManager = new FriendManager();
        GoproHack.m_DiscordManager = new DiscordManager();
        GoproHack.m_DirectoryManager = new DirectoryManager();
        GoproHack.m_CommandManager = new CommandManager();
        GoproHack.m_TickRateManager = new TickRateManager();
        GoproHack.m_NotificationManager = new NotificationManager();
        GoproHack.m_WaypointManager = new WaypointManager();
        GoproHack.m_CapeManager = new CapeManager();
        GoproHack.m_PresetsManager = new PresetsManager();
        GoproHack.m_UUIDManager = new UUIDManager();
    }
}
