// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.command.argument.IArgConsumer;
import java.util.stream.Stream;
import baritone.api.command.ICommand;

final class ce
{
    ICommand a;
    String a;
    ag a;
    
    private ce(final ICommand a, final String a2, final ag a3) {
        this.a = a;
        this.a = a2;
        this.a = a3;
    }
    
    final Stream<String> a() {
        try {
            return this.a.tabComplete(this.a, this.a);
        }
        catch (Throwable t) {
            return Stream.empty();
        }
    }
}
