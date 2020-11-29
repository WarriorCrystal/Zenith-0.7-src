// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.process;

import java.util.List;
import java.io.File;
import baritone.api.schematic.ISchematic;

public interface IBuilderProcess extends IBaritoneProcess
{
    void build(final String p0, final ISchematic p1, final fq p2);
    
    boolean build(final String p0, final File p1, final fq p2);
    
    default boolean build(final String child, final et et) {
        return this.build(child, new File(new File(bib.z().w, "schematics"), child), (fq)et);
    }
    
    void buildOpenSchematic();
    
    void pause();
    
    boolean isPaused();
    
    void resume();
    
    void clearArea(final et p0, final et p1);
    
    List<awt> getApproxPlaceable();
}
