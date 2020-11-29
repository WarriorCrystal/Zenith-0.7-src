// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

final class er implements es
{
    private final es a;
    private final es b;
    private /* synthetic */ en a;
    
    private er(final en a, final es a2, final es b) {
        this.a = a;
        this.a = a2;
        this.b = b;
    }
    
    @Override
    public final int a(final int n, final int n2) {
        if (this.a.a(n, n2) == eu.a) {
            return eu.a;
        }
        return this.b.a(n, n2);
    }
    
    @Override
    public final int a() {
        return Math.min(this.a.a(), this.b.a());
    }
}
