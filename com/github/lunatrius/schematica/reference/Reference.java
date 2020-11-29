// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.reference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reference
{
    public static final String MODID = "schematica";
    public static final String NAME = "Schematica";
    public static final String VERSION = "1.8.0.169";
    public static final String FORGE = "1.12.2-14.23.0.2531";
    public static final String MINECRAFT = "1.12.2";
    public static final String PROXY_SERVER = "com.github.lunatrius.schematica.proxy.ServerProxy";
    public static final String PROXY_CLIENT = "com.github.lunatrius.schematica.proxy.ClientProxy";
    public static final String GUI_FACTORY = "com.github.lunatrius.schematica.client.gui.config.GuiFactory";
    public static Logger logger;
    
    static {
        Reference.logger = LogManager.getLogger("schematica");
    }
}
