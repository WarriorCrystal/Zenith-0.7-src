// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;
import baritone.api.selection.ISelection;
import java.util.function.Function;

enum bv
{
    a(array -> array, new String[] { "all", "a" }), 
    b(array2 -> new ISelection[] { array2[array2.length - 1] }, new String[] { "newest", "n" }), 
    c(array3 -> new ISelection[] { array3[0] }, new String[] { "oldest", "o" });
    
    final Function<ISelection[], ISelection[]> a;
    private final String[] a;
    
    private bv(final Function<ISelection[], ISelection[]> a, final String[] a2) {
        this.a = a;
        this.a = a2;
    }
    
    public static bv a(final String anotherString) {
        bv[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            bv bv;
            String[] a;
            for (int length2 = (a = (bv = values[i]).a).length, j = 0; j < length2; ++j) {
                if (a[j].equalsIgnoreCase(anotherString)) {
                    return bv;
                }
            }
        }
        return null;
    }
    
    public static String[] a() {
        final HashSet set = new HashSet();
        bv[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            set.addAll(Arrays.asList(values[i].a));
        }
        return (String[])set.toArray(new String[0]);
    }
}
