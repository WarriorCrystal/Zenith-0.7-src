// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.reference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reference
{
    public static final String MODID = "lunatriuscore";
    public static final String NAME = "LunatriusCore";
    public static final String VERSION = "1.2.0.42";
    public static final String FORGE = "1.12.2-14.23.0.2531";
    public static final String MINECRAFT = "1.12.2";
    public static final String PROXY_SERVER = "com.github.lunatrius.core.proxy.ServerProxy";
    public static final String PROXY_CLIENT = "com.github.lunatrius.core.proxy.ClientProxy";
    public static Logger logger;
    
    static {
        Reference.logger = LogManager.getLogger("lunatriuscore");
    }
}
