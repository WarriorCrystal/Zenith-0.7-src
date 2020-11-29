// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;

public interface IDatatypeContext
{
    IBaritone getBaritone();
    
    IArgConsumer getConsumer();
}
