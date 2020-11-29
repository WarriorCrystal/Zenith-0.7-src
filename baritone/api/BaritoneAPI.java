// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api;

import java.util.ServiceLoader;
import baritone.api.utils.SettingsUtil;

public final class BaritoneAPI
{
    private static final IBaritoneProvider provider;
    private static final Settings settings;
    
    public static IBaritoneProvider getProvider() {
        return BaritoneAPI.provider;
    }
    
    public static Settings getSettings() {
        return BaritoneAPI.settings;
    }
    
    static {
        SettingsUtil.readAndApply(settings = new Settings());
        provider = ServiceLoader.load(IBaritoneProvider.class).iterator().next();
    }
}
