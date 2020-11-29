// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.util;

import java.util.Iterator;
import com.github.lunatrius.schematica.world.schematic.SchematicFormat;
import org.apache.commons.io.FilenameUtils;
import java.util.Locale;
import java.io.File;
import java.io.FileFilter;

public class FileFilterSchematic implements FileFilter
{
    private final boolean directory;
    
    public FileFilterSchematic(final boolean dir) {
        this.directory = dir;
    }
    
    @Override
    public boolean accept(final File file) {
        if (this.directory) {
            return file.isDirectory();
        }
        final String extension = "." + FilenameUtils.getExtension(file.getName().toLowerCase(Locale.ROOT));
        for (final SchematicFormat format : SchematicFormat.FORMATS.values()) {
            if (format.getExtension().equals(extension)) {
                return true;
            }
        }
        return false;
    }
}
