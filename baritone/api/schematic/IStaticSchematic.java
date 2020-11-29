// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.schematic;

public interface IStaticSchematic extends ISchematic
{
    awt getDirect(final int p0, final int p1, final int p2);
    
    default awt[] getColumn(final int n, final int n2) {
        final awt[] array = new awt[this.heightY()];
        for (int i = 0; i < this.heightY(); ++i) {
            array[i] = this.getDirect(n, i, n2);
        }
        return array;
    }
}
