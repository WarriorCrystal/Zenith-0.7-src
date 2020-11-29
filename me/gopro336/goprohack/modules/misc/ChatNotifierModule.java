// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import java.util.function.Predicate;
import me.gopro336.goprohack.managers.NotificationManager;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.events.goprohack.EventGoproHackModuleDisable;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.goprohack.EventGoproHackModuleEnable;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Module;

public class ChatNotifierModule extends Module
{
    @EventHandler
    private Listener<EventGoproHackModuleEnable> OnModEnable;
    @EventHandler
    private Listener<EventGoproHackModuleDisable> OnModDisable;
    
    public ChatNotifierModule() {
        super("Enable/Disable", new String[] { "" }, "Notifiys you in chat and notification system when a mod is enabled/disabled", "NONE", -1, ModuleType.MISC);
        final String l_Msg;
        this.OnModEnable = new Listener<EventGoproHackModuleEnable>(p_Event -> {
            l_Msg = String.format("%s was enabled.", ChatFormatting.GREEN + p_Event.Mod.getDisplayName() + ChatFormatting.AQUA);
            this.SendMessage(l_Msg);
            NotificationManager.Get().AddNotification("GoproHack", l_Msg);
            return;
        }, (Predicate<EventGoproHackModuleEnable>[])new Predicate[0]);
        final String l_Msg2;
        this.OnModDisable = new Listener<EventGoproHackModuleDisable>(p_Event -> {
            l_Msg2 = String.format("%s was disabled.", ChatFormatting.RED + p_Event.Mod.getDisplayName() + ChatFormatting.AQUA);
            this.SendMessage(l_Msg2);
            NotificationManager.Get().AddNotification("GoproHack", l_Msg2);
        }, (Predicate<EventGoproHackModuleDisable>[])new Predicate[0]);
    }
}
