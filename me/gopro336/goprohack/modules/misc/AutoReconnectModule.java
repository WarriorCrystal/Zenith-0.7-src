// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class AutoReconnectModule extends Module
{
    public final Value<Float> Delay;
    
    public AutoReconnectModule() {
        super("AutoReconnect", new String[] { "Reconnect" }, "Automatically reconnects you to your last server", "NONE", -1, ModuleType.MISC);
        this.Delay = new Value<Float>("Delay", new String[] { "Delay" }, "Delay to use between attempts", 5.0f, 0.0f, 20.0f, 1.0f);
    }
}
