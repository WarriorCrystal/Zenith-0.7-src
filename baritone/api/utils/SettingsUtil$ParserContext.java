// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

import baritone.api.Settings$Setting;

class SettingsUtil$ParserContext
{
    private final Settings$Setting<?> setting;
    
    private SettingsUtil$ParserContext(final Settings$Setting<?> setting) {
        this.setting = setting;
    }
    
    private Settings$Setting<?> getSetting() {
        return this.setting;
    }
}
