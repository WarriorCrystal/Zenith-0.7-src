// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.process;

import java.util.List;
import java.util.function.Predicate;

public interface IFollowProcess extends IBaritoneProcess
{
    void follow(final Predicate<vg> p0);
    
    List<vg> following();
    
    Predicate<vg> currentFilter();
    
    default void cancel() {
        this.onLostControl();
    }
}
