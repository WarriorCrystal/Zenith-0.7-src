// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.HashMap;
import baritone.api.utils.input.Input;
import java.util.Map;
import baritone.api.pathing.movement.MovementStatus;

public final class cn
{
    public MovementStatus a;
    public co a;
    final Map<Input, Boolean> a;
    
    public cn() {
        this.a = new co();
        this.a = new HashMap<Input, Boolean>();
    }
    
    public final cn a(final co a) {
        this.a = a;
        return this;
    }
    
    public final cn a(final Input input, final boolean b) {
        this.a.put(input, b);
        return this;
    }
}
