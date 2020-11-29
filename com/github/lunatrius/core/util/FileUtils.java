// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.util;

import java.io.IOException;
import com.github.lunatrius.core.reference.Reference;
import java.io.File;

public class FileUtils
{
    public static String humanReadableByteCount(final long bytes) {
        final int unit = 1024;
        if (bytes < 1024L) {
            return bytes + " B";
        }
        final int exp = (int)(Math.log((double)bytes) / Math.log(1024.0));
        final String pre = "KMGTPE".charAt(exp - 1) + "i";
        return String.format("%3.0f %sB", bytes / Math.pow(1024.0, exp), pre);
    }
    
    public static boolean contains(final File root, final String filename) {
        return contains(root, new File(root, filename));
    }
    
    public static boolean contains(final File root, final File file) {
        try {
            return file.getCanonicalPath().startsWith(root.getCanonicalPath() + File.separator);
        }
        catch (IOException e) {
            Reference.logger.error("", (Throwable)e);
            return false;
        }
    }
}
