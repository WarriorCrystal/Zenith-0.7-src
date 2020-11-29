// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;

enum cc
{
    a(new String[] { "list", "get", "l" }), 
    b(new String[] { "clear", "c" }), 
    c(new String[] { "save", "s" }), 
    d(new String[] { "info", "show", "i" }), 
    e(new String[] { "delete", "d" }), 
    f(new String[] { "goal", "goto", "g" });
    
    private final String[] a;
    
    private cc(final String[] a) {
        this.a = a;
    }
    
    public static cc a(final String anotherString) {
        cc[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            cc cc;
            String[] a;
            for (int length2 = (a = (cc = values[i]).a).length, j = 0; j < length2; ++j) {
                if (a[j].equalsIgnoreCase(anotherString)) {
                    return cc;
                }
            }
        }
        return null;
    }
    
    public static String[] a() {
        final HashSet set = new HashSet();
        cc[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            set.addAll(Arrays.asList(values[i].a));
        }
        return (String[])set.toArray(new String[0]);
    }
}
