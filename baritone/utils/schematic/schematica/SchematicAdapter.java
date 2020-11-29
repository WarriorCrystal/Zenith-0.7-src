// 
// Decompiled by Procyon v0.5.36
// 

package baritone.utils.schematic.schematica;

import java.util.List;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import baritone.api.schematic.IStaticSchematic;

public final class SchematicAdapter implements IStaticSchematic
{
    private final SchematicWorld a;
    
    public SchematicAdapter(final SchematicWorld a) {
        this.a = a;
    }
    
    @Override
    public final awt desiredState(final int n, final int n2, final int n3, final awt awt, final List<awt> list) {
        return this.getDirect(n, n2, n3);
    }
    
    @Override
    public final awt getDirect(final int n, final int n2, final int n3) {
        return this.a.getSchematic().getBlockState(new et(n, n2, n3));
    }
    
    @Override
    public final int widthX() {
        return this.a.getSchematic().getWidth();
    }
    
    @Override
    public final int heightY() {
        return this.a.getSchematic().getHeight();
    }
    
    @Override
    public final int lengthZ() {
        return this.a.getSchematic().getLength();
    }
}
