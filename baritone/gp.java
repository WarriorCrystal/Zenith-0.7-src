// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Optional;
import java.io.File;
import java.util.function.Consumer;
import java.util.Arrays;
import baritone.api.schematic.format.ISchematicFormat;
import baritone.api.command.registry.Registry;
import baritone.api.schematic.ISchematicSystem;

public enum gp implements ISchematicSystem
{
    a;
    
    private final Registry<ISchematicFormat> a;
    
    private gp() {
        this.a = new Registry<ISchematicFormat>();
        Arrays.stream(gr.values()).forEach(this.a::register);
    }
    
    @Override
    public final Registry<ISchematicFormat> getRegistry() {
        return this.a;
    }
    
    @Override
    public final Optional<ISchematicFormat> getByFile(final File file) {
        return this.a.stream().filter(schematicFormat -> schematicFormat.isFileType(file)).findFirst();
    }
}
