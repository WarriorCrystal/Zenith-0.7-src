// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.schematic.ISchematicSystem;
import baritone.api.command.ICommandSystem;
import baritone.api.cache.IWorldScanner;
import java.util.Collections;
import baritone.api.IBaritone;
import java.util.List;
import baritone.api.IBaritoneProvider;

public final class BaritoneProvider implements IBaritoneProvider
{
    private final a a;
    private final List<IBaritone> a;
    
    public BaritoneProvider() {
        this.a = new a();
        this.a = (List<IBaritone>)Collections.singletonList(this.a);
        new x(this.a);
    }
    
    @Override
    public final IBaritone getPrimaryBaritone() {
        return this.a;
    }
    
    @Override
    public final List<IBaritone> getAllBaritones() {
        return this.a;
    }
    
    @Override
    public final IWorldScanner getWorldScanner() {
        return w.a;
    }
    
    @Override
    public final ICommandSystem getCommandSystem() {
        return y.a;
    }
    
    @Override
    public final ISchematicSystem getSchematicSystem() {
        return gp.a;
    }
}
