// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.concurrent.LinkedBlockingQueue;

final class o implements Runnable
{
    private /* synthetic */ n a;
    
    private o(final n a) {
        this.a = a;
    }
    
    @Override
    public final void run() {
        LinkedBlockingQueue a;
        while ((a = this.a.a) != null) {
            try {
                n.a(this.a, p.a(a.take()));
                continue;
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
                return;
            }
            catch (Throwable t) {
                t.printStackTrace();
                continue;
            }
            break;
        }
    }
}
