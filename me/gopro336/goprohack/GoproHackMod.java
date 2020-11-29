// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack;

import me.zero.alpine.fork.bus.EventManager;
import org.apache.logging.log4j.LogManager;
import me.gopro336.goprohack.main.ForgeEventProcessor;
import net.minecraftforge.common.MinecraftForge;
import me.gopro336.goprohack.main.GoproHack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import me.zero.alpine.fork.bus.EventBus;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "goprohack", name = "Zenith", version = "Beta 0.6")
public final class GoproHackMod
{
    public static final String NAME = "Zenith";
    public static final String VERSION = "Beta 0.6";
    public static final Logger log;
    public static final EventBus EVENT_BUS;
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        GoproHackMod.log.info("init goprohack v: Beta 0.6");
        GoproHack.Init();
        MinecraftForge.EVENT_BUS.register((Object)new ForgeEventProcessor());
    }
    
    static {
        log = LogManager.getLogger("gopro");
        EVENT_BUS = new EventManager();
    }
}
