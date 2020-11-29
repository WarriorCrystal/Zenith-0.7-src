// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import java.util.Map;
import net.minecraftforge.fml.common.SidedProxy;
import com.github.lunatrius.schematica.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "schematica", name = "Schematica", version = "1.8.0.169", guiFactory = "com.github.lunatrius.schematica.client.gui.config.GuiFactory")
public class Schematica
{
    @Mod.Instance("schematica")
    public static Schematica instance;
    @SidedProxy(serverSide = "com.github.lunatrius.schematica.proxy.ServerProxy", clientSide = "com.github.lunatrius.schematica.proxy.ClientProxy")
    public static CommonProxy proxy;
    
    @NetworkCheckHandler
    public boolean checkModList(final Map<String, String> versions, final Side side) {
        return true;
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        Schematica.proxy.preInit(event);
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        Schematica.proxy.init(event);
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        Schematica.proxy.postInit(event);
    }
    
    @Mod.EventHandler
    public void serverStarting(final FMLServerStartingEvent event) {
        Schematica.proxy.serverStarting(event);
    }
}
