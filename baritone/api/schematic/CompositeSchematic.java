// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.schematic;

import java.util.ArrayList;
import java.util.List;

public class CompositeSchematic extends AbstractSchematic
{
    private final List<CompositeSchematicEntry> schematics;
    private CompositeSchematicEntry[] schematicArr;
    
    private void recalcArr() {
        this.schematicArr = this.schematics.toArray(new CompositeSchematicEntry[0]);
        CompositeSchematicEntry[] schematicArr;
        for (int length = (schematicArr = this.schematicArr).length, i = 0; i < length; ++i) {
            final CompositeSchematicEntry compositeSchematicEntry = schematicArr[i];
            this.x = Math.max(this.x, compositeSchematicEntry.x + compositeSchematicEntry.schematic.widthX());
            this.y = Math.max(this.y, compositeSchematicEntry.y + compositeSchematicEntry.schematic.heightY());
            this.z = Math.max(this.z, compositeSchematicEntry.z + compositeSchematicEntry.schematic.lengthZ());
        }
    }
    
    public CompositeSchematic(final int n, final int n2, final int n3) {
        super(n, n2, n3);
        this.schematics = new ArrayList<CompositeSchematicEntry>();
        this.recalcArr();
    }
    
    public void put(final ISchematic schematic, final int n, final int n2, final int n3) {
        this.schematics.add(new CompositeSchematicEntry(schematic, n, n2, n3));
        this.recalcArr();
    }
    
    private CompositeSchematicEntry getSchematic(final int n, final int n2, final int n3, final awt awt) {
        CompositeSchematicEntry[] schematicArr;
        for (int length = (schematicArr = this.schematicArr).length, i = 0; i < length; ++i) {
            final CompositeSchematicEntry compositeSchematicEntry = schematicArr[i];
            if (n >= compositeSchematicEntry.x && n2 >= compositeSchematicEntry.y && n3 >= compositeSchematicEntry.z && compositeSchematicEntry.schematic.inSchematic(n - compositeSchematicEntry.x, n2 - compositeSchematicEntry.y, n3 - compositeSchematicEntry.z, awt)) {
                return compositeSchematicEntry;
            }
        }
        return null;
    }
    
    @Override
    public boolean inSchematic(final int n, final int n2, final int n3, final awt awt) {
        final CompositeSchematicEntry schematic;
        return (schematic = this.getSchematic(n, n2, n3, awt)) != null && schematic.schematic.inSchematic(n - schematic.x, n2 - schematic.y, n3 - schematic.z, awt);
    }
    
    @Override
    public awt desiredState(final int n, final int n2, final int n3, final awt awt, final List<awt> list) {
        final CompositeSchematicEntry schematic;
        if ((schematic = this.getSchematic(n, n2, n3, awt)) == null) {
            throw new IllegalStateException("couldn't find schematic for this position");
        }
        return schematic.schematic.desiredState(n - schematic.x, n2 - schematic.y, n3 - schematic.z, awt, list);
    }
}
