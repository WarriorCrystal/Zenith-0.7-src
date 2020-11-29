// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.command.datatypes.NearbyPlayer;
import baritone.api.command.datatypes.EntityClassById;
import baritone.api.command.datatypes.IDatatypeFor;

enum bd
{
    a((IDatatypeFor)EntityClassById.INSTANCE), 
    b((IDatatypeFor)NearbyPlayer.INSTANCE);
    
    final IDatatypeFor a;
    
    private bd(final IDatatypeFor a) {
        this.a = a;
    }
}
