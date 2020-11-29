// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.schematic;

import java.util.List;

public interface ISchematic
{
    default boolean inSchematic(final int n, final int n2, final int n3, final awt awt) {
        return n >= 0 && n < this.widthX() && n2 >= 0 && n2 < this.heightY() && n3 >= 0 && n3 < this.lengthZ();
    }
    
    default int size(final fa$a obj) {
        switch (ISchematic$1.$SwitchMap$net$minecraft$util$EnumFacing$Axis[obj.ordinal()]) {
            case 1: {
                return this.widthX();
            }
            case 2: {
                return this.heightY();
            }
            case 3: {
                return this.lengthZ();
            }
            default: {
                throw new UnsupportedOperationException(String.valueOf(obj));
            }
        }
    }
    
    awt desiredState(final int p0, final int p1, final int p2, final awt p3, final List<awt> p4);
    
    int widthX();
    
    int heightY();
    
    int lengthZ();
}
