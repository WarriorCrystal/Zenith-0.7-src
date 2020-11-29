// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

import java.lang.reflect.Type;

interface SettingsUtil$ISettingParser<T>
{
    T parse(final SettingsUtil$ParserContext p0, final String p1);
    
    String toString(final SettingsUtil$ParserContext p0, final T p1);
    
    boolean accepts(final Type p0);
}
