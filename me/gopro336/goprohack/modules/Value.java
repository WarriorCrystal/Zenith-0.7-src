// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules;

public class Value<T>
{
    private String name;
    private String[] alias;
    private String desc;
    private Module Mod;
    public ValueListeners Listener;
    private T value;
    private T min;
    private T max;
    private T inc;
    
    public Value(final String name, final String[] alias, final String desc) {
        this.name = name;
        this.alias = alias;
        this.desc = desc;
    }
    
    public Value(final String name, final String[] alias, final String desc, final T value) {
        this(name, alias, desc);
        this.value = value;
    }
    
    public Value(final String name, final String[] alias, final String desc, final T value, final T min, final T max, final T inc) {
        this(name, alias, desc, value);
        this.min = min;
        this.max = max;
        this.inc = inc;
    }
    
    public <T> T clamp(final T value, final T min, final T max) {
        return (((Comparable)value).compareTo(min) < 0) ? min : ((((Comparable)value).compareTo(max) > 0) ? max : value);
    }
    
    public T getValue() {
        return this.value;
    }
    
    public void setValue(final T value) {
        if (this.min != null && this.max != null) {
            final Number val = (Number)value;
            final Number min = (Number)this.min;
            final Number max = (Number)this.max;
            this.value = (T)val;
        }
        else {
            this.value = value;
        }
        if (this.Mod != null) {
            this.Mod.SignalValueChange(this);
        }
        if (this.Listener != null) {
            this.Listener.OnValueChange(this);
        }
    }
    
    public String GetNextEnumValue(final boolean p_Reverse) {
        final Enum l_CurrEnum = this.getValue();
        int i;
        for (i = 0; i < this.value.getClass().getEnumConstants().length; ++i) {
            final Enum e = (Enum)this.value.getClass().getEnumConstants()[i];
            if (e.name().equalsIgnoreCase(l_CurrEnum.name())) {
                break;
            }
        }
        return this.value.getClass().getEnumConstants()[(p_Reverse ? ((i != 0) ? (i - 1) : (this.value.getClass().getEnumConstants().length - 1)) : (i + 1)) % this.value.getClass().getEnumConstants().length].toString();
    }
    
    public int getEnum(final String input) {
        for (int i = 0; i < this.value.getClass().getEnumConstants().length; ++i) {
            final Enum e = (Enum)this.value.getClass().getEnumConstants()[i];
            if (e.name().equalsIgnoreCase(input)) {
                return i;
            }
        }
        return -1;
    }
    
    public Enum GetEnumReal(final String input) {
        for (int i = 0; i < this.value.getClass().getEnumConstants().length; ++i) {
            final Enum e = (Enum)this.value.getClass().getEnumConstants()[i];
            if (e.name().equalsIgnoreCase(input)) {
                return e;
            }
        }
        return null;
    }
    
    public void setEnumValue(final String value) {
        for (final Enum e : (Enum[])((Enum)this.value).getClass().getEnumConstants()) {
            if (e.name().equalsIgnoreCase(value)) {
                this.setValue(e);
                break;
            }
        }
        if (this.Mod != null) {
            this.Mod.SignalEnumChange();
        }
    }
    
    public T getMin() {
        return this.min;
    }
    
    public void setMin(final T min) {
        this.min = min;
    }
    
    public T getMax() {
        return this.max;
    }
    
    public void setMax(final T max) {
        this.max = max;
    }
    
    public T getInc() {
        return this.inc;
    }
    
    public void setInc(final T inc) {
        this.inc = inc;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String[] getAlias() {
        return this.alias;
    }
    
    public void setAlias(final String[] alias) {
        this.alias = alias;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(final String desc) {
        this.desc = desc;
    }
    
    public void SetListener(final ValueListeners p_VListener) {
        this.Listener = p_VListener;
    }
    
    public void InitalizeMod(final Module p_Mod) {
        this.Mod = p_Mod;
    }
    
    public void SetForcedValue(final T value) {
        if (this.min != null && this.max != null) {
            final Number val = (Number)value;
            final Number min = (Number)this.min;
            final Number max = (Number)this.max;
            this.value = (T)val;
        }
        else {
            this.value = value;
        }
    }
}
