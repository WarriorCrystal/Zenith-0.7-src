// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api;

import java.lang.reflect.Type;
import baritone.api.utils.SettingsUtil;
import baritone.api.utils.TypeUtils;

public final class Settings$Setting<T>
{
    public T value;
    public final T defaultValue;
    private String name;
    final /* synthetic */ Settings this$0;
    
    private Settings$Setting(final Settings this$0, final T t) {
        this.this$0 = this$0;
        if (t == null) {
            throw new IllegalArgumentException("Cannot determine value type class from null");
        }
        this.value = t;
        this.defaultValue = t;
    }
    
    @Deprecated
    public final T get() {
        return this.value;
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final Class<T> getValueClass() {
        return (Class<T>)TypeUtils.resolveBaseClass(this.getType());
    }
    
    @Override
    public final String toString() {
        return SettingsUtil.settingToString(this);
    }
    
    public final void reset() {
        this.value = this.defaultValue;
    }
    
    public final Type getType() {
        return this.this$0.settingTypes.get(this);
    }
}
