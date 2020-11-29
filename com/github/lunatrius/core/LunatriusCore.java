// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import java.util.Map;
import net.minecraftforge.fml.common.SidedProxy;
import com.github.lunatrius.core.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "lunatriuscore", name = "LunatriusCore", version = "1.2.0.42")
public class LunatriusCore
{
    @SidedProxy(serverSide = "com.github.lunatrius.core.proxy.ServerProxy", clientSide = "com.github.lunatrius.core.proxy.ClientProxy")
    public static CommonProxy proxy;
    
    @NetworkCheckHandler
    public boolean checkModList(final Map<String, String> versions, final Side side) {
        return true;
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        LunatriusCore.proxy.preInit(event);
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        LunatriusCore.proxy.init(event);
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        LunatriusCore.proxy.postInit(event);
    }
    
    @Mod.EventHandler
    public void processIMC(final FMLInterModComms.IMCEvent event) {
        LunatriusCore.proxy.processIMC(event);
    }
}
