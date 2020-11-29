// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

public enum gl
{
    a(0), 
    b(1), 
    c(2), 
    d(3);
    
    public final boolean[] a;
    
    private gl(final int n) {
        this.a = new boolean[] { (n & 0x2) != 0x0, (n & 0x1) != 0x0 };
    }
    
    public static gl a(final boolean b, final boolean b2) {
        if (b) {
            if (b2) {
                return gl.d;
            }
            return gl.c;
        }
        else {
            if (b2) {
                return gl.b;
            }
            return gl.a;
        }
    }
}
