// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.schematic.format;

import java.io.File;
import baritone.api.schematic.IStaticSchematic;
import java.io.InputStream;

public interface ISchematicFormat
{
    IStaticSchematic parse(final InputStream p0);
    
    boolean isFileType(final File p0);
}
