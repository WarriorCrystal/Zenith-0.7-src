// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.schematic;

public abstract class AbstractSchematic implements ISchematic
{
    protected int x;
    protected int y;
    protected int z;
    
    public AbstractSchematic() {
        this(0, 0, 0);
    }
    
    public AbstractSchematic(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public int widthX() {
        return this.x;
    }
    
    @Override
    public int heightY() {
        return this.y;
    }
    
    @Override
    public int lengthZ() {
        return this.z;
    }
}
