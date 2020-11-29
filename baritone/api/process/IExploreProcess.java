// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.process;

import java.nio.file.Path;

public interface IExploreProcess extends IBaritoneProcess
{
    void explore(final int p0, final int p1);
    
    void applyJsonFilter(final Path p0, final boolean p1);
}
