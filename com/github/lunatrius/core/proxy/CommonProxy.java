// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.proxy;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import com.google.common.collect.UnmodifiableIterator;
import com.github.lunatrius.core.handler.ConfigurationHandler;
import com.github.lunatrius.core.version.VersionChecker;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import com.github.lunatrius.core.reference.Reference;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public abstract class CommonProxy
{
    public void preInit(final FMLPreInitializationEvent event) {
        Reference.logger = event.getModLog();
        FMLInterModComms.sendMessage("lunatriuscore", "checkUpdate", "1.12.2-14.23.0.2531");
    }
    
    public void init(final FMLInitializationEvent event) {
    }
    
    public void postInit(final FMLPostInitializationEvent event) {
        if (VersionChecker.isAllowedToCheck("Global") && ConfigurationHandler.VersionCheck.checkForUpdates) {
            VersionChecker.startVersionCheck();
        }
    }
    
    public void processIMC(final FMLInterModComms.IMCEvent event) {
        for (final FMLInterModComms.IMCMessage message : event.getMessages()) {
            if ("checkUpdate".equals(message.key) && message.isStringMessage()) {
                this.processMessage(message.getSender(), message.getStringValue());
            }
        }
    }
    
    private void processMessage(final String sender, final String forgeVersion) {
        final ModContainer container = Loader.instance().getIndexedModList().get(sender);
        if (container != null) {
            VersionChecker.registerMod(container, forgeVersion);
        }
    }
}
