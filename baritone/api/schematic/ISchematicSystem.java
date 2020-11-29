// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.schematic;

import java.util.Optional;
import java.io.File;
import baritone.api.schematic.format.ISchematicFormat;
import baritone.api.command.registry.Registry;

public interface ISchematicSystem
{
    Registry<ISchematicFormat> getRegistry();
    
    Optional<ISchematicFormat> getByFile(final File p0);
}
