// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.schematic;

public class CompositeSchematicEntry
{
    public final ISchematic schematic;
    public final int x;
    public final int y;
    public final int z;
    
    public CompositeSchematicEntry(final ISchematic schematic, final int x, final int y, final int z) {
        this.schematic = schematic;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
