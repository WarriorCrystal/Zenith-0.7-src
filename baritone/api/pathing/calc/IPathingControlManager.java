// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.pathing.calc;

import baritone.api.process.PathingCommand;
import java.util.Optional;
import baritone.api.process.IBaritoneProcess;

public interface IPathingControlManager
{
    void registerProcess(final IBaritoneProcess p0);
    
    Optional<IBaritoneProcess> mostRecentInControl();
    
    Optional<PathingCommand> mostRecentCommand();
}
