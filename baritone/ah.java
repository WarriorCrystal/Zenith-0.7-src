// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.datatypes.IDatatypeContext;

final class ah implements IDatatypeContext
{
    private /* synthetic */ ag a;
    
    private ah(final ag a) {
        this.a = a;
    }
    
    @Override
    public final IBaritone getBaritone() {
        return this.a.a.getBaritone();
    }
}
