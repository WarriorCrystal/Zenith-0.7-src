// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.schematic.IStaticSchematic;
import java.io.InputStream;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import baritone.api.schematic.format.ISchematicFormat;

public enum gr implements ISchematicFormat
{
    a("MCEDIT", "schematic") {
        gs(final String s, final String s2) {
        }
        
        @Override
        public final IStaticSchematic parse(final InputStream inputStream) {
            return new gu(gi.a(inputStream));
        }
    }, 
    b("SPONGE", "schem") {
        gt(final String s, final String s2) {
        }
        
        @Override
        public final IStaticSchematic parse(final InputStream inputStream) {
            final fy a;
            switch ((a = gi.a(inputStream)).h("Version")) {
                case 1:
                case 2: {
                    return new gv(a);
                }
                default: {
                    throw new UnsupportedOperationException("Unsupported Version of a Sponge Schematic");
                }
            }
        }
    };
    
    private final String a;
    
    private gr(final String a) {
        this.a = a;
    }
    
    @Override
    public boolean isFileType(final File file) {
        return this.a.equalsIgnoreCase(FilenameUtils.getExtension(file.getAbsolutePath()));
    }
}
