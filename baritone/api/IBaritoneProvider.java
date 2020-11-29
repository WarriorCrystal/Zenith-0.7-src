// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api;

import baritone.api.schematic.ISchematicSystem;
import baritone.api.command.ICommandSystem;
import baritone.api.cache.IWorldScanner;
import java.util.Iterator;
import java.util.Objects;
import java.util.List;

public interface IBaritoneProvider
{
    IBaritone getPrimaryBaritone();
    
    List<IBaritone> getAllBaritones();
    
    default IBaritone getBaritoneForPlayer(final bud a) {
        for (final IBaritone baritone : this.getAllBaritones()) {
            if (Objects.equals(a, baritone.getPlayerContext().player())) {
                return baritone;
            }
        }
        return null;
    }
    
    IWorldScanner getWorldScanner();
    
    ICommandSystem getCommandSystem();
    
    ISchematicSystem getSchematicSystem();
}
