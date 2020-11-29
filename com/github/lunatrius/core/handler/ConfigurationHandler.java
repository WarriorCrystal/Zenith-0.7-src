// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.handler;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler
{
    public static Configuration configuration;
    
    @SubscribeEvent
    public void onConfigurationChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase("lunatriuscore")) {
            ConfigManager.sync("lunatriuscore", Config.Type.INSTANCE);
        }
    }
    
    @Config(modid = "lunatriuscore", category = "versioncheck")
    public static class VersionCheck
    {
        @Config.RequiresMcRestart
        @Config.Comment({ "Should the mod check for updates?" })
        public static boolean checkForUpdates;
        
        static {
            VersionCheck.checkForUpdates = true;
        }
    }
}
