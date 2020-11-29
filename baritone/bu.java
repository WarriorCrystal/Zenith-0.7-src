// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;

enum bu
{
    a(new String[] { "pos1", "p1", "1" }), 
    b(new String[] { "pos2", "p2", "2" }), 
    c(new String[] { "clear", "c" }), 
    d(new String[] { "undo", "u" }), 
    e(new String[] { "set", "fill", "s", "f" }), 
    f(new String[] { "walls", "w" }), 
    g(new String[] { "shell", "shl" }), 
    h(new String[] { "cleararea", "ca" }), 
    i(new String[] { "replace", "r" }), 
    j(new String[] { "expand", "ex" }), 
    k(new String[] { "contract", "ct" }), 
    l(new String[] { "shift", "sh" });
    
    private final String[] a;
    
    private bu(final String[] a) {
        this.a = a;
    }
    
    public static bu a(final String anotherString) {
        bu[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            bu bu;
            String[] a;
            for (int length2 = (a = (bu = values[i]).a).length, j = 0; j < length2; ++j) {
                if (a[j].equalsIgnoreCase(anotherString)) {
                    return bu;
                }
            }
        }
        return null;
    }
    
    public static String[] a() {
        final HashSet set = new HashSet();
        bu[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            set.addAll(Arrays.asList(values[i].a));
        }
        return (String[])set.toArray(new String[0]);
    }
}
