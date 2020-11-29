// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.function.Predicate;
import java.util.OptionalInt;
import baritone.api.schematic.ISchematic;
import baritone.api.schematic.IStaticSchematic;
import baritone.api.schematic.MaskSchematic;

public final class go extends MaskSchematic
{
    private final int[][] a;
    
    public go(final IStaticSchematic staticSchematic) {
        super(staticSchematic);
        final int[][] a = new int[staticSchematic.widthX()][staticSchematic.lengthZ()];
        for (int i = 0; i < staticSchematic.widthX(); ++i) {
            int j = 0;
        Label_0038:
            while (j < staticSchematic.lengthZ()) {
                final awt[] column = staticSchematic.getColumn(i, j);
                final Predicate<awt> predicate = awt -> !(awt.u() instanceof aom);
                final awt[] array = column;
                while (true) {
                    for (int k = column.length - 1; k >= 0; --k) {
                        if (predicate.test(array[k])) {
                            final OptionalInt optionalInt2;
                            final OptionalInt optionalInt = optionalInt2 = OptionalInt.of(k);
                            final OptionalInt optionalInt3 = optionalInt2;
                            if (optionalInt.isPresent()) {
                                a[i][j] = optionalInt3.getAsInt();
                            }
                            else {
                                System.out.println("Column " + i + "," + j + " has no blocks, but it's apparently map art? wtf");
                                System.out.println("Letting it be whatever");
                                a[i][j] = 256;
                            }
                            ++j;
                            continue Label_0038;
                        }
                    }
                    OptionalInt optionalInt2;
                    final OptionalInt optionalInt = optionalInt2 = OptionalInt.empty();
                    continue;
                }
            }
        }
        this.a = a;
    }
    
    public final boolean partOfMask(final int n, final int n2, final int n3, final awt awt) {
        return n2 >= this.a[n][n3];
    }
}
