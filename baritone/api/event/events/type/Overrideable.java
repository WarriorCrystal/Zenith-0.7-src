// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.event.events.type;

public class Overrideable<T>
{
    private T value;
    private boolean modified;
    
    public Overrideable(final T value) {
        this.value = value;
    }
    
    public T get() {
        return this.value;
    }
    
    public void set(final T value) {
        this.value = value;
        this.modified = true;
    }
    
    public boolean wasModified() {
        return this.modified;
    }
    
    @Override
    public String toString() {
        return String.format("Overrideable{modified=%b,value=%s}", this.modified, this.value.toString());
    }
}
